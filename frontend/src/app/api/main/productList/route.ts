import { NextResponse } from "next/server";
import { Product, ProductResponseDto } from "@/app/types/Product";

// api 에서 데이터를 가져옴
async function fetchProducts(): Promise<Product[]> {
  try {
    const response = await fetch('http://localhost:8080/api/main/productList');
    const rawData = await response.json();

    // API 응답을 Product 형식으로 변환
    const products: Product[] = rawData.map((item: any) => ({
      id: item.id, // API의 필드명에 따라 수정
      name: item.name,
      price: item.price,
      quantity: item.quantity,
      imgPath: item.imgPath,
      created_date: item.created_date,
      modify_date: item.modify_date,
      description: item.description,
      admin: {
        id: item.admin.id,
      }
    }));

    return products;
  } catch (error) {
    console.error('상품 데이터를 가져오는 중 오류 발생:', error);
    return [];
  }
}

export async function GET() {
  const products = await fetchProducts();
  const data: ProductResponseDto = {
    products,
  };
  return NextResponse.json(data);
}
