"use client";
import { useEffect, useState } from "react";
import { Product } from "./types/Product";

const getProductList = async () => {
  const res = await fetch("/api/main/productList");

  if (!res.ok) {
    throw new Error("Failed to fetch product list");
  }

  const data = await res.json();
  return data;
};

export default function Home() {
  const [cartItems, setCartItems] = useState<Product[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");

  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    // 데이터 fetching
    const fetchData = async () => {
      try {
        const data = await getProductList();
        setProducts(data.products); // 데이터를 상태에 저장
      } catch (err) {
        console.log("상품 목록을 가져오는 데 문제가 발생했습니다.");
      }
    };

    fetchData();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 호출

  const handleAddToCart = (product: Product) => {
    setCartItems((prevItems) => {
      const existingItem = prevItems.find((item) => item.id === product.id);
      if (existingItem) {
        return prevItems.map((item) =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }
      return [...prevItems, { ...product, quantity: 1 }];
    });
  };
  const handleRemoveFromCart = (productId: number) => {
    setCartItems((prevItems) => {
      const existingItem = prevItems.find((item) => item.id === productId);
      if (existingItem && existingItem.quantity > 1) {
        return prevItems.map((item) =>
          item.id === productId
            ? { ...item, quantity: item.quantity - 1 }
            : item
        );
      }
      return prevItems.filter((item) => item.id !== productId);
    });
  };
  // 총 금액 계산 함수
  const calculateTotal = () => {
    return cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  };

  const handlePayment = async () => {
    try {
      const response = await fetch("/api/main/order", {
        method: "POST",
        body: JSON.stringify({}),
      });

      if (!response.ok) {
        throw new Error("주문에 실패했습니다.");
      }

      const data = await response.json();
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  };

  // 검색어에 따라 필터링된 상품 목록을 반환하는 함수
  const filteredProducts = products.filter((product) =>
    product.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="flex justify-center mb-4">
        <h1 className="text-3xl font-bold text-center py-4">Grids & Circle</h1>
      </div>

      <div className="max-w-[px] w-[90%] mx-auto bg-white rounded-2xl shadow-lg">
        <div className="flex flex-col md:flex-row">
          {/* 상품 목록 섹션 */}
          <div className="md:w-2/3 mt-4 flex flex-col items-start p-3 pt-0">
            <div className="w-full flex justify-between items-center mb-4">
              <h5 className="font-bold">상품 목록</h5>
              <div className="relative">
                <input
                  type="text"
                  placeholder="상품 검색..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-800"
                />
              </div>
            </div>
            <ul className="w-full space-y-2 mt-3">
              {filteredProducts.map((product) => (
                <li
                  key={product.id}
                  className="flex justify-between items-center p-3 border rounded-lg hover:bg-gray-50"
                >
                  <div>
                    <h6 className="font-semibold">{product.name}</h6>
                    <p className="text-sm text-gray-600">
                      {product.description}
                    </p>
                    <p className="text-blue-600 font-medium">
                      {product.price.toLocaleString()}원
                    </p>
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
              {cartItems.map((item) => (
                <h6 key={item.id} className="flex items-center justify-between">
                  <span
                    className="ml-2 px-2 py-1 bg-gray-800 text-white text-sm rounded cursor-pointer"
                    onClick={() => handleRemoveFromCart(item.id)}
                  >
                    {item.name} - {item.price.toLocaleString()}원
                  </span>
                  <span className="bg-blue-500 text-white px-2 py-1 rounded-full text-xs">
                    {item.quantity}개
                  </span>
                </h6>
              ))}
            </div>
            <hr className="my-8 border-2 border-gray-500" />

            {/* 주문 정보 입력 섹션 */}
            <form className="mt-4 space-y-4">
              <div>
                <label htmlFor="email" className="block mb-1">
                  이메일 (예시: example@example.com)
                </label>
                <input
                  type="email"
                  id="email"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div>
                <label htmlFor="address" className="block mb-1">
                  주소 (예시: 서울시 서초구 반포대로 3)
                </label>
                <input
                  type="text"
                  id="address"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div>
                <label htmlFor="postcode" className="block mb-1">
                  우편번호 (예시: 06711)
                </label>
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
              <h5 className="font-bold">
                {calculateTotal().toLocaleString()}원
              </h5>
            </div>

            <button
              onClick={handlePayment}
              className="w-full bg-gray-800 text-white py-2 rounded-md mt-4 hover:bg-gray-700"
            >
              결제하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
