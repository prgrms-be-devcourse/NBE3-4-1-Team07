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
      admin: {
        id: item.admin.id,
        username: item.admin.username,
        password: item.admin.password
      }
    }));
    
    return products;
  } catch (error) {
    console.error('상품 데이터를 가져오는 중 오류 발생:', error);
    return [];
  }
}

export function getProducts(){
  return data.products;
}

export function addProduct(product: Product){
  products.push(product);
}

export function saveProducts(updatedProducts: Product[]) {
  products = updatedProducts;
}

export async function GET() {
  const products = await fetchProducts();
  const data: ProductReponseDto = {
    products,
  };
  return NextResponse.json(data);
}
