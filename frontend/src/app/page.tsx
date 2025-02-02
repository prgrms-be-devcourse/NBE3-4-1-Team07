"use client";
import { useEffect, useState } from "react";
import { Product } from "./types/Product";
import Image from "next/image";
import { OrderRequestDto } from './types/Order';

const getProductList = async () => {
  const res = await fetch("/api/main/productList");

  if (!res.ok) {
    throw new Error("Failed to fetch product list");
  }

  const data = await res.json();
  return data;
};

export default function Home() {
  // 초기 상태는 빈 배열로 설정
  const [cartItems, setCartItems] = useState<Product[]>([]);
  
  // 별도의 useEffect에서 localStorage 데이터 로드
  useEffect(() => {
    try {
      const savedCart = localStorage.getItem('cartItems');
      if (savedCart) {
        setCartItems(JSON.parse(savedCart));
      }
    } catch (error) {
      console.error('장바구니 데이터 로드 실패:', error);
    }
  }, []); // 컴포넌트 마운트 시 한 번만 실행

  // localStorage 저장
  useEffect(() => {
    try {
      localStorage.setItem('cartItems', JSON.stringify(cartItems));
    } catch (error) {
      console.error('장바구니 데이터 저장 실패:', error);
    }
  }, [cartItems]);

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

  const [sortOrder, setSortOrder] = useState<'high' | 'low'>('high');
  const [selectedItems, setSelectedItems] = useState<number[]>([]);

  const handleAddToCart = (product: Product) => {
    setCartItems(prevItems => {
      const newItems = prevItems.find(item => item.id === product.id)
        ? prevItems.map(item =>
            item.id === product.id
              ? { ...item, quantity: item.quantity + 1 }
              : item
          )
        : [...prevItems, { ...product, quantity: 1 }];
      return newItems;
    });
    setSelectedItems(prev => prev.filter(id => id !== product.id));
  };
  const handleRemoveFromCart = (productId: number) => {
    setCartItems(prevItems => {
      const existingItem = prevItems.find(item => item.id === productId);
      if (existingItem && existingItem.quantity > 1) {
        return prevItems.map(item =>
            item.id === productId
                ? { ...item, quantity: item.quantity - 1 }
                : item
        );
      }
      setSelectedItems(prev => prev.filter(id => id !== productId));
      return prevItems.filter(item => item.id !== productId);
    });
  };
  // 총 금액 계산 함수
  const calculateTotal = () => {
    return cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  };

  // 사용자 입력을 위한 상태 추가
  const [orderInfo, setOrderInfo] = useState({
    email: '',
    address: '',
    postcode: ''
  });

  // 입력 핸들러 추가
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setOrderInfo(prev => ({
      ...prev,
      [id]: value
    }));
  };
  // 결제버튼 클릭시 주문 처리
  const handlePayment = async () => {
    try {
      const orderData: OrderRequestDto = {
        email: orderInfo.email,
        address: orderInfo.address,
        postalCode: orderInfo.postcode,
        totalPrice: calculateTotal(),
        products: cartItems.map(item => ({
          productId: item.id,
          quantity: item.quantity
        }))
      };
      
      // 장바구니 상품 ID 확인을 위한 로그 추가
      console.log('장바구니 상품 ID 목록:', cartItems.map(item => item.id));
      console.log('서버로 보내는 데이터:', orderData);
      console.log('JSON 형태:', JSON.stringify(orderData, null, 2));

      const response = await fetch("/api/main/order", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(orderData),
      });

      if (!response.ok) {
        const errorData = await response.text(); // 에러 메시지 확인
        console.error('서버 응답:', errorData);
        throw new Error("주문에 실패했습니다.");
      }

      const data = await response.json();
      console.log('주문 성공:', data);

      // 주문 성공 시 카트 비우기
      setCartItems([]);
      setOrderInfo({ email: '', address: '', postcode: '' });
      alert('주문이 성공적으로 완료되었습니다!');
    } catch (error) {
      console.error('주문 처리 중 오류:', error);
      alert('주문 처리 중 오류가 발생했습니다.');
    }
  };

  // 정렬과 검색이 적용된 상품 목록을 반환하는 함수
  const getFilteredAndSortedProducts = () => {
    return products
        .filter(product =>
            product.name.toLowerCase().includes(searchTerm.toLowerCase())
        )
        .sort((a, b) => {
          if (sortOrder === 'high') {
            return b.price - a.price; // 높은 가격순
          } else {
            return a.price - b.price; // 낮은 가격순
          }
        });
  };

  const handleItemSelect = (itemId: number) => {
    setSelectedItems(prev =>
        prev.includes(itemId)
            ? prev.filter(id => id !== itemId)
            : [...prev, itemId]
    );
  };

  const handleDeleteSelected = () => {
    setCartItems(prev => prev.filter(item => !selectedItems.includes(item.id)));
    setSelectedItems([]);
  };

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
                <div className="flex gap-4 items-center">
                  <select
                      value={sortOrder}
                      onChange={(e) => setSortOrder(e.target.value as 'high' | 'low')}
                      className="px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-800"
                  >
                    <option value="high">높은 가격순</option>
                    <option value="low">낮은 가격순</option>
                  </select>
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
              </div>
              <ul className="w-full space-y-2 mt-3">
                {getFilteredAndSortedProducts().map((product) => (
                    <li key={product.id} className="flex justify-between items-center p-3 border rounded-lg hover:bg-gray-50">
                      <div className="flex items-center gap-4">
                        <div className="w-20 h-20 relative">
                          <Image
                              src={`http://localhost:8080${product.imgPath}`}
                              alt={product.name}
                              fill
                              className="object-cover rounded-lg"
                              sizes="(max-width: 768px) 80px, 80px"
                          />
                        </div>
                        <div>
                          <h6 className="font-semibold">{product.name}</h6>
                          <p className="text-blue-600 font-medium">{product.price.toLocaleString()}원</p>
                        </div>
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
              <div className="flex justify-between items-center">
                <h5 className="font-bold">Cart Items</h5>
                {selectedItems.length > 0 && (
                    <button
                        onClick={handleDeleteSelected}
                        className="px-3 py-1 bg-red-500 text-white text-sm rounded hover:bg-red-600 transition"
                    >
                      선택 삭제
                    </button>
                )}
              </div>
              <hr className="my-4" />
              {/* Cart Items 섹션 */}
              <div className="space-y-2" id="cartItems">
                {cartItems.map((item) => (
                    <div key={item.id} className="flex items-center justify-between gap-2">
                      <div className="flex items-center gap-2">
                        <input
                            type="checkbox"
                            checked={selectedItems.includes(item.id)}
                            onChange={() => handleItemSelect(item.id)}
                            className="w-4 h-4 rounded border-gray-300 focus:ring-2 focus:ring-gray-800"
                        />
                        <span
                            className="px-2 py-1 bg-gray-800 text-white text-sm rounded cursor-pointer"
                            onClick={() => handleRemoveFromCart(item.id)}
                        >
                      {item.name} - {item.price.toLocaleString()}원
                    </span>
                      </div>
                      <span className="bg-blue-500 text-white px-2 py-1 rounded-full text-xs">
                    {item.quantity}개
                  </span>
                    </div>
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
                      value={orderInfo.email}
                      onChange={handleInputChange}
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
                      value={orderInfo.address}
                      onChange={handleInputChange}
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
                      value={orderInfo.postcode}
                      onChange={handleInputChange}
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