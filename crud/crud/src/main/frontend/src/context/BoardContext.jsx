// BoardContext.js
import React, { createContext, useContext, useState, useEffect } from "react";

/* ✅ 1. Context 생성 */
const BoardContext = createContext();

/* ✅ 2. useBoard 훅 생성 + export */
export function useBoard() {
  return useContext(BoardContext);
}

export function BoardProvider({ children }) {
  const [posts, setPosts] = useState([]);
  const [likes, setLikes] = useState({});
  const currentUserId = 1;

  // ----------------------------
  // 게시글 불러오기
  // ----------------------------
  const fetchPosts = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/posts");
      const data = await res.json();
      setPosts(data);
      await fetchLikeCounts(data);
    } catch (error) {
      console.error("게시글 로딩 오류:", error);
    }
  };

  const fetchLikeCounts = async (loadedPosts) => {
    const likeCounts = {};
    await Promise.all(
      loadedPosts.map(async (post) => {
        try {
          const res = await fetch(
            `http://localhost:8080/api/posts/${post.id}/likes`
          );
          likeCounts[post.id] = await res.json();
        } catch {
          likeCounts[post.id] = 0;
        }
      })
    );
    setLikes(likeCounts);
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const togglePostLike = async (postId) => {
    try {
      await fetch(`http://localhost:8080/api/posts/${postId}/like`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId: currentUserId }),
      });

      const res = await fetch(
        `http://localhost:8080/api/posts/${postId}/likes`
      );
      const count = await res.json();

      setLikes((prev) => ({ ...prev, [postId]: count }));
      setPosts((prev) =>
        prev.map((post) =>
          post.id === postId ? { ...post, likes: count } : post
        )
      );
    } catch (error) {
      console.error("좋아요 오류:", error);
    }
  };

  return (
    <BoardContext.Provider
      value={{
        posts,
        likes,
        togglePostLike,
      }}
    >
      {children}
    </BoardContext.Provider>
  );
}
