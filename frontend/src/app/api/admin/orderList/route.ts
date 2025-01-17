import { Order, OrderRequestDto, OrderResponseDto } from "@/app/types/Order";
import { NextResponse } from "next/server";

const orders: Order[] = [
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

const data: OrderResponseDto = {
  orders,
};

//GET /api/admin/orderList

export async function GET() {
  return NextResponse.json(data);
}
