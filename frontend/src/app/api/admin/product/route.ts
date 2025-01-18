import {addProduct, getProducts} from "@/app/api/main/productList/route";
import {NextRequest, NextResponse} from "next/server";


// /api/admin/product
export async function POST(request: NextRequest) {
    try {
        const body = await request.json();
        const products = getProducts();

        // 배열에서 마지막 요소의 id 값 가져오기
        const lastId = products.length > 0 ? products[products.length - 1].id : 0;

        // 새로운 주문 추가
        const newOrder = { id: lastId + 1, ...body };

        addProduct(newOrder);

        return NextResponse.json({
            message: "새로운 주문이 추가되었습니다.",
            order: newOrder,
        });
    } catch (error) {
        return NextResponse.json(
            { message: "상품 등록 실패", error: error.message },
            { status: 500 }
        );
    }
}