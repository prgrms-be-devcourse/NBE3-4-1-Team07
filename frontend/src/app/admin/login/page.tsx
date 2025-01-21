"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function AdminLogin() {
  const [loginInfo, setLoginInfo] = useState({
    username: "",
    password: "",
  });
  const router = useRouter();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setLoginInfo(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      const response = await fetch("/api/admin/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginInfo),
      });

      const data = await response.json();
      console.log('로그인 응답:', data);

      if (!response.ok) {
        throw new Error(data.message || "로그인 실패");
      }

      if (data.message.includes("Welcome")) {
        alert("로그인 성공");
        router.push("/admin");
      } else {
        throw new Error(data.message || "로그인 실패");
      }

    } catch (error) {
      console.error("로그인 오류:", error);
      alert(error instanceof Error ? error.message : "로그인에 실패했습니다.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-md w-96">
        <h1 className="text-2xl font-bold text-center mb-6">관리자 로그인</h1>
        
        <form onSubmit={handleLogin} className="space-y-4">
          <div>
            <label htmlFor="username" className="block text-sm font-medium text-gray-700">
              아이디
            </label>
            <input
              type="text"
              id="username"
              name="username"
              value={loginInfo.username}
              onChange={handleInputChange}
              className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 focus:border-gray-500 focus:outline-none"
              required
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">
              비밀번호
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={loginInfo.password}
              onChange={handleInputChange}
              className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 focus:border-gray-500 focus:outline-none"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full bg-gray-800 text-white py-2 px-4 rounded-md hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
          >
            로그인
          </button>
        </form>
      </div>
    </div>
  );
} 