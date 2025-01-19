import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  try {
    const orderData = await request.json();
    
    // Spring 백엔드로 요청 전달
    const response = await fetch('http://localhost:8080/api/main/order', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(orderData)
    });

    if (!response.ok) {
      throw new Error('백엔드 서버 에러');
    }

    const data = await response.json();
    return NextResponse.json(data);
    
  } catch (error) {
    console.error('주문 처리 중 오류:', error);
    return NextResponse.json(
      { message: '주문 처리 중 오류가 발생했습니다.' },
      { status: 500 }
    );
  }
}