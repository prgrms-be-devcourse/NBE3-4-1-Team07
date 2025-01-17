"use client";

import React, { useEffect, useState } from "react";
import {Order, OrderResponseDto} from "../types/Order";
import { Sidebar, Menu, MenuItem } from 'react-pro-sidebar';
import FormatListBulletedOutlinedIcon from '@mui/icons-material/FormatListBulletedOutlined';
import Image from "next/image";
import {Product, ProductReponseDto} from "@/app/types/Product";
import {Button, FormControl, Modal, Typography} from "@mui/material";
import {Box} from "@mui/system";

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

const getOrderList = async () => {
  const res = await fetch("/api/admin/orderList");

  if (!res.ok) {
    throw new Error("주문 목록 조회 실패");
  }

  const data = await res.json();
  return data;
};

const getProductList = async () => {
  const res = await fetch("/api/main/productList");

  if (!res.ok) {
    throw new Error("상품 목록 조회 실패");
  }

  const data = await res.json();
  return data;
};

export default function admin() {
  // 주문 목록 조회가 디폴트
  const [activeMenu, setActiveMenu] = useState("orderList");
  const [orders, setOrders] = useState<Order[]>([]);
  const [products, setProduts] = useState<Product[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);

  useEffect(() => {
    // 데이터 fetching
    const fetchData = async () => {
      try {
        if(activeMenu == "orderList"){
          const data: OrderResponseDto = await getOrderList();
          setOrders(data.orders); // 데이터를 상태에 저장
        } else if(activeMenu == "productList"){
          const data: ProductReponseDto = await getProductList();
          setProduts(data.products);
        }
      } catch (err) {
        console.log("데이터를 가져오는 데 문제가 발생했습니다.");
      }
    };

    fetchData();
  }, [activeMenu]); // 컴포넌트가 처음 렌더링될 때 한 번만 호출

  const [filters, setFilters] = useState({
    searchType: "",
    keyword: "",
    dateFrom: "",
    dateTo: "",
  });

  const handleMenuClick = (menu: string) => setActiveMenu(menu);


  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters({ ...filters, [name]: value });
  };

  const handleOpen = async (id: number) => {
    try{
      const response: Response = await fetch(`/api/admin/product/${id}`);

      if(!response.ok){
        throw new Error("상품 정보를 불러오는데 실패했습니다.");
      }

      const data: Product = await response.json();
      console.log(data)

      setSelectedProduct(data);
      setIsModalOpen(true);
      return data;
    } catch(error){
      console.error(error);
      return null;
    }
  };

  const handleClose = () => {
    setIsModalOpen(false);
    setSelectedProduct(null);
  };

  const handleAddProduct = () => {

  }



  return (
      <div className="flex h-screen">
        <Sidebar backgroundColor="#FBFBFB" className="min-h-full">
          <Menu>
            <MenuItem
                onClick={() => handleMenuClick("orderList")}
                icon={<FormatListBulletedOutlinedIcon/>}>주문목록조회</MenuItem>
            <MenuItem
                onClick={() => handleMenuClick("productList")}>등록상품조회</MenuItem>
          </Menu>
        </Sidebar>
        <div className="w-3/4 p-4 flex-grow overflow-auto">
          {activeMenu === "orderList" && (
            <div>
              {
                <div className="flex-grow overflow-auto">
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
                          <input type="checkbox"/>
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
                              <input type="checkbox"/>
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
              }
              {/* ... */}
            </div>)}
          {activeMenu === "productList" && (
              <div>
                <h1 className="text-xl font-bold mb-4">상품 목록</h1>
                <Button onClick={handleAddProduct}>추가</Button>
                <ul className="w-full space-y-2 mt-3">
                  {products.map((product: Product) => (
                      <li key={product.id} className="flex justify-between items-center p-3 border rounded-lg hover:bg-gray-50">
                        <div className="flex items-center gap-4">
                          <div className="w-20 h-20 relative">
                            <Image
                                src={product.imgPath}
                                alt={product.name}
                                fill
                                className="object-cover rounded-lg"
                                sizes="(max-width: 768px) 80px, 80px"
                            />
                          </div>
                          <div>
                            <h6 className="font-semibold">{product.name}</h6>
                            <p className="text-sm text-gray-600">{product.description}</p>
                            <p className="text-blue-600 font-medium">{product.price.toLocaleString()}원</p>
                          </div>
                        </div>
                        <button
                            className="px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700 transition"
                            onClick={() => handleOpen(product.id)}
                        >
                          상세
                        </button>
                      </li>
                  ))}</ul>
              </div>)}
        </div>
        <Modal
            open={isModalOpen}
            onClose={handleClose}
            aria-labelledby="parent-modal-title"
            aria-describedby="parent-modal-description"
        >
          <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              모달 제목
            </Typography>
            <FormControl>
              <input/>
              <button>저장</button>
              <button onClick={handleClose}>닫기</button>
            </FormControl>
          </Box>
        </Modal>
      </div>
  );
}
