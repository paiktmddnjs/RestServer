// AuthContext.js
import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export function AuthProvider({ children }) {
  // 초기 상태를 null 또는 세션에서 가져온 값으로 설정
  const [user, setUser] = useState(null); 

  // -----------------------------------------
  // 🔥 API 연동: 로그인
  // -----------------------------------------
  const login = async (id, pw) => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ id, pw }),
      });

      if (!res.ok) {
        // 백엔드에서 400 에러와 함께 보낸 에러 메시지를 alert
        const errorText = await res.text();
        alert(`로그인 실패: ${errorText}`);
        return false;
      }

      // 로그인 성공 시, 백엔드에서 받은 사용자 정보를 파싱
      const loggedInUser = await res.json();
      setUser(loggedInUser);
      // 필요하다면, 사용자 정보를 세션 스토리지 등에 저장 (간단한 예제이므로 생략)
      return true;

    } catch (error) {
      console.error("로그인 중 오류 발생:", error);
      alert("서버와 통신에 실패했습니다.");
      return false;
    }
  };

  const logout = () => {
    // 실제라면 서버에 로그아웃 요청을 보내 세션/토큰을 무효화해야 합니다.
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}