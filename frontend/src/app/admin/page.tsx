"use client";
import { useState } from "react";

type Order = {
  id: number;
  email: string;
  address: string;
  postalCode: string;
  state: string;
  totalPrice: number;
  orderDate: string;
};

type OrderRequestDto = {
  orders: Order[];
};

export default function admi() {
  const [filters, setFilters] = useState({
    searchType: "",
    keyword: "",
    dateFrom: "",
    dateTo: "",
  });

  const orders = [
    {
      id: 1,
      address: "서울시 강남구 역삼동 123-45",
      email: "test1@test.com",
      postalCode: "00000",
      state: "주문접수",
      totalPrice: 100000,
      orderDate: "2025-01-15 12:00:00",
    },
    {
      id: 2,
      address: "서울시 강남구 역삼동 123-45",
      email: "test2@test.com",
      postalCode: "00000",
      state: "주문접수",
      totalPrice: 100000,
      orderDate: "2025-01-15 12:00:00",
    },
    {
      id: 3,
      address: "강원도 원주시 효동로 123-45",
      email: "test3@test.com",
      postalCode: "00000",
      state: "주문접수",
      totalPrice: 100000,
      orderDate: "2025-01-15 12:00:00",
    },
    {
      id: 4,
      address: "충청남도 천안시 동남구 목천읍 중앙로 123-45",
      email: "test4@test.com",
      postalCode: "00000",
      state: "주문접수",
      totalPrice: 100000,
      orderDate: "2025-01-15 12:00:00",
    },
    {
      id: 5,
      address: "경기도 안산시 단대로 123-45",
      email: "test5@test.com",
      postalCode: "00000",
      state: "주문접수",
      totalPrice: 100000,
      orderDate: "2025-01-15 12:00:00",
    },
    {
      id: 6,
      address: "경상남도 합천군 합천읍 합천로 123-45",
      email: "test6@test.com",
      postalCode: "00000",
      state: "주문접수",
      totalPrice: 100000,
      orderDate: "2025-01-15 12:00:00",
    },
  ];

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters({ ...filters, [name]: value });
  };

  return (
    <div className="p-4">
      <h1 className="text-xl font-bold mb-4">주문 목록</h1>

      {/* Filters */}
      <div className="flex items-center space-x-2 mb-4">
        <select
          name="searchType"
          value={filters.searchType}
          onChange={handleFilterChange}
          className="border rounded px-2 py-1"
        >
          <option value="아이디">이메일</option>
        </select>
        <input
          type="text"
          name="keyword"
          value={filters.keyword}
          onChange={handleFilterChange}
          placeholder="검색어"
          className="border rounded px-2 py-1"
        />

        <input
          type="date"
          name="dateFrom"
          value={filters.dateFrom}
          onChange={handleFilterChange}
          className="border rounded px-2 py-1"
        />
        <span>~</span>
        <input
          type="date"
          name="dateTo"
          value={filters.dateTo}
          onChange={handleFilterChange}
          className="border rounded px-2 py-1"
        />
        <button className="bg-gray-800 text-white px-4 py-2 rounded">
          조회
        </button>
      </div>

      {/* User List */}
      <div>
        <table className="w-full border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border px-4 py-2">
                <input type="checkbox" />
              </th>
              <th className="border px-4 py-2">주문번호</th>
              <th className="border px-4 py-2">이메일</th>
              <th className="border px-4 py-2">주문일자</th>
              <th className="border px-4 py-2">주소</th>
              <th className="border px-4 py-2">우편번호</th>
              <th className="border px-4 py-2">총 금액</th>
              <th className="border px-4 py-2">상태</th>
              <th className="border px-4 py-2">주문상세</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order, index) => (
              <tr key={index} className="text-center">
                <td className="border px-4 py-2">
                  <input type="checkbox" />
                </td>
                <td className="border px-4 py-2">{order.id}</td>
                <td className="border px-4 py-2">{order.email}</td>
                <td className="border px-4 py-2">{order.orderDate}</td>
                <td className="border px-4 py-2">{order.address}</td>
                <td className="border px-4 py-2">{order.postalCode}</td>
                <td className="border px-4 py-2">{order.totalPrice}</td>
                <td className="border px-4 py-2">{order.state}</td>
                <td className="border px-4 py-2">
                  <button className="bg-blue-500 text-white px-4 py-1 rounded">
                    정보
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
