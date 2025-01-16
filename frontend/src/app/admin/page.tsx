"use client";
import { useEffect, useState } from "react";
import { Order } from "../types/Order";

const getOrderList = async () => {
  const res = await fetch("/api/admin/orderList");

  if (!res.ok) {
    throw new Error("Failed to fetch order list");
  }

  const data = await res.json();
  return data;
};

export default function admin() {
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => {
    // 데이터 fetching
    const fetchData = async () => {
      try {
        const data = await getOrderList();
        setOrders(data.orders); // 데이터를 상태에 저장
      } catch (err) {
        console.log("주문 목록을 가져오는 데 문제가 발생했습니다.");
      }
    };

    fetchData();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 호출

  const [filters, setFilters] = useState({
    searchType: "",
    keyword: "",
    dateFrom: "",
    dateTo: "",
  });

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
