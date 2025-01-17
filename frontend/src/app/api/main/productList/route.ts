import { NextResponse } from "next/server";
import { Product, ProductReponseDto } from "@/app/types/Product";

// 상품 목록 데이터 (예시)
let products: Product[] = [
  {
    id: 1,
    name: "스타벅스",
    price: 12000,
    description: "아메리카노",
    quantity: 1,
    imgPath: "/images/starbucks.jpg"
  },
  {
    id: 2,
    name: "이디야",
    price: 8000,
    description: "토피넛 라테",
    quantity: 1,
    imgPath: "/images/ediya.jpg"
  },
  {
    id: 3,
    name: "커피빈",
    price: 6000,
    description: "카푸치노",
    quantity: 1,
    imgPath: "/images/coffeebean.jpg"
  },
  {
    id: 4,
    name: "브루노",
    price: 9000,
    description: "브루노 마스",
    quantity: 1,
    imgPath: "/images/bruno.jpg"
  },
];

const data: ProductReponseDto = {
  products,
};

export function getProducts(){
  return data.products;
}

export function addProduct(product: Product){
  products.push(product);
}

export async function GET() {
  return NextResponse.json(data);
}
