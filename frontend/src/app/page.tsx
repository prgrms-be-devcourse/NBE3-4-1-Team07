import Image from "next/image";
'use client';
import { useState } from 'react';

interface Product {
  id: number;
  name: string;
  price: number;
  description: string;
}

export default function Home() {
  const [cartItems, setCartItems] = useState<Product[]>([]);
  // 상품 목록 데이터 (예시)
  const products: Product[] = [
    { id: 1, name: "스타벅스", price: 12000, description: "아메리카노" },
    { id: 2, name: "이디야", price: 8000, description: "토피넛 라테" },
    { id: 3, name: "커피빈", price: 6000, description: "카푸치노" },
    { id: 4, name: "브루노", price: 9000, description: "브루노 마스" },
  ];
    const handleAddToCart = (product: Product) => {
      setCartItems([...cartItems, product]);
    };
    const handleRemoveFromCart = (index: number) => {
      setCartItems(cartItems.filter((_, i) => i !== index));
    };
    // 총 금액 계산 함수
    const calculateTotal = () => {
      return cartItems.reduce((sum, item) => sum + item.price, 0);
    };

  return (
    <div className="container-fluid p-4">
      <div className="flex justify-center mb-4">
        <h1 className="text-center">Grids & Circle</h1>
      </div>
      
      <div className="max-w-[950px] w-[90%] mx-auto bg-white rounded-2xl shadow-lg">
        <div className="flex flex-col md:flex-row">
          {/* 상품 목록 섹션 */}
          <div className="md:w-2/3 mt-4 flex flex-col items-start p-3 pt-0">
            <h5 className="font-bold">상품 목록</h5>
            <ul className="w-full space-y-2 mt-3">
              {products.map((product) => (
              <li key={product.id} className="flex justify-between items-center p-3 border rounded-lg hover:bg-gray-50">
                <div>
                  <h6 className="font-semibold">{product.name}</h6>
                  <p className="text-sm text-gray-600">{product.description}</p>
                  <p className="text-blue-600 font-medium">{product.price.toLocaleString()}원</p>
                </div>
                <button 
                  onClick={() => handleAddToCart(product)}
                  className="px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700 transition"
                >
                  담기
                </button>
              </li>
            ))}
            </ul>
          </div>

          {/* Summary 섹션 */}
          <div className="md:w-1/3 bg-gray-200 p-4 rounded-r-2xl">
            <div>
              <h5 className="font-bold">Cart Items</h5>
            </div>
            <hr className="my-4" />

            {/* Cart Items 섹션 */}
            <div className="space-y-2" id="cartItems">
              {cartItems.map((item, index) => (
                <h6 key={index} className="flex items-center">
                  <span className="ml-2 px-2 py-1 bg-gray-800 text-white text-sm rounded"
                  onClick={() => handleRemoveFromCart(index)}>
                    {item.name} - {item.price.toLocaleString()}원
                  </span>
                </h6>
              ))}
            </div>

            {/* 주문 정보 입력 섹션 */}
            <form className="mt-4 space-y-4">
              <div>
                <label htmlFor="email" className="block mb-1">이메일</label>
                <input
                  type="email"
                  id="email"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div>
                <label htmlFor="address" className="block mb-1">주소</label>
                <input
                  type="text"
                  id="address"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div>
                <label htmlFor="postcode" className="block mb-1">우편번호</label>
                <input
                  type="text"
                  id="postcode"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div className="text-sm">
                당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
              </div>
            </form>

            <div className="flex justify-between items-center border-t mt-4 pt-4">
              <h5 className="font-bold">총금액</h5>
              <h5 className="font-bold">{calculateTotal().toLocaleString()}원</h5>
            </div>
            
            <button className="w-full bg-gray-800 text-white py-2 rounded-md mt-4 hover:bg-gray-700">
              결제하기
            </button>

          </div>
        </div>
      </div>
    </div>
  );
}
