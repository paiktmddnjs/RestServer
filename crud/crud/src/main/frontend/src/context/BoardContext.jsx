// BoardContext.js
import React, { createContext, useContext, useState, useEffect } from "react";
// ... (생략) ...

export function BoardProvider({ children }) {
  const [posts, setPosts] = useState([]);
  const [likes, setLikes] = useState({}); // 🌟 새로 추가: 좋아요 상태 저장 { postId: boolean }
  const currentUserId = 1;

  // -----------------------------------------
  // 🔥 1. 서버에서 게시글 불러오기 및 초기 좋아요 상태 설정
  // -----------------------------------------
  const fetchPosts = async () => {
    try {
      // 1-1. 게시글 목록 로드
      const res = await fetch("http://localhost:8080/api/posts");
      const data = await res.json();
      setPosts(data);

      // 1-2. 게시글 로드 후, 좋아요 카운트 로드 (TopPosts.jsx에서 필요)
      await fetchLikeCounts(data);

    } catch (error) {
      console.error("게시글 로딩 오류:", error);
    }
  };

  // -----------------------------------------
  // 🔥 (추가) 좋아요 카운트 가져오기 (TopPosts.jsx에서 사용)
  // -----------------------------------------
  const fetchLikeCounts = async (loadedPosts) => {
      // 모든 게시물의 좋아요 수를 동시에 가져와서 likes 상태를 업데이트합니다.
      const likeCounts = {};
      await Promise.all(loadedPosts.map(async (post) => {
          try {
              const res = await fetch(`http://localhost:8080/api/posts/${post.id}/likes`);
              const count = await res.json();
              likeCounts[post.id] = count;
          } catch(error) {
              likeCounts[post.id] = 0;
          }
      }));
      setLikes(likeCounts); // postLikeCounts가 likes로 이름 변경되었다고 가정
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  // -----------------------------------------
  // 🔥 2. 게시글 추가 (addBoard) - 이전 코드와 동일
  // -----------------------------------------
  const addBoard = async (category, score, title, content, author, date, image) => {
    // ... (이전 코드와 동일, POST /api/posts 호출)
    // ...
  };

  // -----------------------------------------
  // 🔥 3. 좋아요 토글 (togglePostLike) - BoardList.jsx에서 사용
  // -----------------------------------------
  const togglePostLike = async (postId) => {
      try {
          // 서버로 좋아요 요청 보내기 (이전 toggleLike 함수와 동일)
          await fetch(`http://localhost:8080/api/posts/${postId}/like`, {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify({ userId: currentUserId }) // userId를 보냄
          });

          // 좋아요 카운트 다시 로드하여 TopPosts/BoardList에 반영
          // (성능 최적화를 위해 특정 게시물의 카운트만 업데이트하는 것이 좋습니다.)
          const res = await fetch(`http://localhost:8080/api/posts/${postId}/likes`);
          const count = await res.json();

          setLikes(prev => ({ // likes 상태 업데이트
              ...prev,
              [postId]: count
          }));

          // 전체 posts 리스트도 업데이트 (게시글 상세 페이지의 '좋아요 수' 필드를 위한 갱신)
          setPosts(prev =>
              prev.map(post =>
                  post.id === postId ? { ...post, likes: count } : post
              )
          );
      } catch (error) {
          console.error("좋아요 오류:", error);
      }
  };

  // -----------------------------------------
  // 🔥 4. 게시글 삭제 (deleteBoard) - BoardDetail.jsx에서 사용
  // -----------------------------------------
  const deleteBoard = async (postId) => {
    try {
      await fetch(`http://localhost:8080/api/posts/${postId}`, {
        method: "DELETE", // DELETE 요청
      });

      // 프론트 상태 업데이트
      setPosts(prev => prev.filter(post => post.id !== postId));

    } catch (error) {
      console.error("게시글 삭제 오류:", error);
    }
  };

  // -----------------------------------------
  // 🔥 5. 게시글 수정 (updateBoard) - WriteBoard.jsx에서 사용
  // -----------------------------------------
  const updateBoard = async (id, title, content, author, category, date, image) => {
      const updatedPost = { id, title, content, author, category, date, image };

      try {
          const res = await fetch(`http://localhost:8080/api/posts/${id}`, {
              method: "PUT", // PUT 요청
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify(updatedPost),
          });

          const saved = await res.json();

          // 프론트 상태 업데이트
          setPosts(prev => prev.map(post => post.id === id ? saved : post));

      } catch (error) {
          console.error("게시글 수정 오류:", error);
      }
  };


  // -----------------------------------------
  // Provider로 값 전달
  // -----------------------------------------
  return (
    <BoardContext.Provider value={{
      posts,
      likes, // 🌟 likes 상태 추가
      addBoard,
      deleteBoard, // 🌟 deleteBoard 함수 추가
      updateBoard, // 🌟 updateBoard 함수 추가
      togglePostLike, // 🌟 togglePostLike 함수 추가 (이전 toggleLike 대체)
    }}>
      {children}
    </BoardContext.Provider>
  );
}