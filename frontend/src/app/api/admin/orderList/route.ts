import { Order } from "@/app/types/Order";
import { NextResponse } from "next/server";


// GET /admin/orderList
export async function GET() {
  try {

    const response = await fetch('http://localhost:8080/admin/orderList');
    const rawData = await response.json();

    const orderList: Order[] = rawData.map((item: any) => ({
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
    throw error;
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