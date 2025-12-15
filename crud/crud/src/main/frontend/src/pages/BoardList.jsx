import React from 'react';
import { useNavigate } from "react-router-dom";
import { useBoard } from "../context/BoardContext";
import {
  Container,
  WriteBox,
  StyledLink,
  List,
  Card,
  HomeLinkWrapper,
  DateText,
  HeartButton
} from "./BoardList.styled";

//  PostCard 컴포넌트를 BoardList 함수 정의 외부로 독립적으로 정의합니다.
const PostCard = ({ post, navigate, likes = {}, togglePostLike }) => {
  // likes props에 기본값 {}을 설정하여, 혹시라도 undefined가 들어와도 안전하게 접근할 수 있도록 보장합니다.
  const isLiked = !!likes[post.id]; 

  const handleLikeClick = (e) => {
    e.stopPropagation(); // Card 클릭 이벤트 방지
    togglePostLike(post.id); // Context 함수 호출
  };

  return (
    <Card onClick={() => navigate(`/board/${post.id}`)}>
      {/* 제목 */}
      <h2>{post.title}</h2> 

      {/* 내용 */}
      <p>{post.content}</p>

      {/*  평점 */}
      <div style={{ fontSize: "20px", margin: 0 }}>
        {"⭐".repeat(post.score)}
      </div>

      {/*  날짜 오른쪽 */}
      <DateText>{post.date}</DateText>
      
      {/* 클릭 가능한 하트 버튼 (인기도) */}
      <HeartButton onClick={handleLikeClick} isLiked={isLiked}>
        {isLiked ? '❤️' : '🤍'}
      </HeartButton>
    </Card>
  );
};

function BoardList() {
  // useBoard에서 likes 상태를 가져옵니다.
  // Context가 아직 초기화되지 않았거나 posts를 로드하는 중이라면 likes가 빈 객체({})이거나 undefined일 수 있습니다.
  // PostCard에 기본값({})을 설정하여 안전하게 처리합니다.
  const { posts, likes, togglePostLike } = useBoard();
  const navigate = useNavigate();

  return (
    <Container>
      <h1>🍽️ 맛평가 게시판</h1>

      <WriteBox>
        <StyledLink to="/write">글쓰기</StyledLink>
      </WriteBox>

      <List>
        {posts.map((post) => (
          <PostCard 
            key={post.id} 
            post={post} 
            navigate={navigate} 
            likes={likes}
            togglePostLike={togglePostLike}
          />
        ))}
      </List>

      <HomeLinkWrapper>
        <StyledLink to="/">홈으로 가기</StyledLink>
      </HomeLinkWrapper>
    </Container>
  );
}

export default BoardList;