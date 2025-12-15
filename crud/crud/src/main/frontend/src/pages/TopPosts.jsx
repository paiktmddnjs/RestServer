import React, { useMemo } from 'react';
import { useBoard } from '../context/BoardContext';
import { 
  Wrapper, Container, Title, SubTitle, List, PostCard,
  RankBadge, PostTitle, PostDetails, LikeCount, ContentSummary, ViewDetailLink
} from './TopPosts.styled';

function TopPosts() {
  // useBoard에서 posts와 postLikeCounts를 가져옵니다.
  const { posts, postLikeCounts } = useBoard();

  // useMemo를 사용하여 좋아요 수 계산 및 정렬을 캐시합니다.
  const topPosts = useMemo(() => {
    
    const safePostLikeCounts = postLikeCounts || {};
    
    // 1. posts 데이터와 좋아요 수를 병합
    const postsWithLikes = posts.map(post => ({
      ...post,
      // postLikeCounts는 문자열 키를 가지므로 post.id를 문자열로 변환
      likeCount: safePostLikeCounts[String(post.id)] || 0,
    }));

    // 2. 좋아요 수 기준으로 내림차순 정렬
    const sortedPosts = postsWithLikes.sort((a, b) => b.likeCount - a.likeCount);

    // 3. 상위 3개만 선택
    return sortedPosts.slice(0, 3);
  }, [posts, postLikeCounts]);


  return (
    <Wrapper>
      <Container>
        <Title>🏆 BEST 3 리뷰 🏆</Title>
        <SubTitle>가장 많은 '좋아요'를 받은 인기 맛평가입니다.</SubTitle>

        <List>
          {topPosts.length === 0 ? (
            <p style={{ color: '#555', fontSize: '1.1rem', padding: '30px', background: '#fff', borderRadius: '12px', boxShadow: '0 2px 10px rgba(0,0,0,0.05)' }}>
              아직 작성된 리뷰가 없거나 좋아요 수가 집계되지 않았습니다.
            </p>
          ) : (
            topPosts.map((post, index) => (
              
              <PostCard 
                key={post.id} 
                to={`/board/${post.id}`} 
                $rank={index + 1}
              >
                
                {/* 순위 배지 */}
                <RankBadge $rank={index + 1}>{index + 1}위</RankBadge>
                
                <PostTitle>{post.title}</PostTitle>

                <ContentSummary>
                  {post.content}
                </ContentSummary>

                <PostDetails>
                  <span>
                    작성자: {post.author} ({post.category})
                  </span>
                  <LikeCount>
                    {post.likeCount}
                  </LikeCount>
                </PostDetails>
                
             
             
              </PostCard>
            ))
          )}
        </List>
        
        <div style={{ marginTop: '40px' }}>
          <ViewDetailLink to="/board" style={{ color: '#007bff' }}>
            전체 리뷰 목록으로 돌아가기
          </ViewDetailLink>
        </div>
      </Container>
    </Wrapper>
  );
}

export default TopPosts;