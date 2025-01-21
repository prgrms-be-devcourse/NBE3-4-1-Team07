import { Order } from "@/app/types/Order";
import { NextResponse } from "next/server";


// GET /admin/orderList
export async function GET() {
  try {
    const response = await fetch('http://localhost:8080/api/admin/orderList', {
      credentials: 'include',  // 쿠키 포함
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      }
    });

    if (!response.ok) {
      throw new Error('주문 목록 조회 실패');
    }

<<<<<<< Updated upstream
=======
    const response = await fetch('admin/orderList');
>>>>>>> Stashed changes
    const rawData = await response.json();
    
    // 응답이 이미 배열인지 확인
    const ordersData = Array.isArray(rawData) ? rawData : rawData.orders;

    const orderList: Order[] = ordersData.map((item: any) => ({
      id: item.id,
      email: item.email,
      address: item.address,
      postalCode: item.postalCode,
      state: item.state,
      totalPrice: item.totalPrice,
      orderDate: formatDate(item.orderDate),
    }));

    return NextResponse.json(orderList);
  } catch (error) {
    console.error('주문 목록 조회 실패:', error);
    return NextResponse.json(
      { message: "주문 목록 조회 실패" },
      { status: 500 }
    );
  }
}

// 날짜 형식 변환 함수
function formatDate(dateString: string): string {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}`;
}