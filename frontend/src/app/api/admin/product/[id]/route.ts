import {getProducts} from "@/app/api/main/productList/route";
import {Product} from "@/app/types/Product";
import {NextRequest, NextResponse} from "next/server";


// GET /api/admin/product/${id}

export async function GET(request: NextRequest, { params }: { params: { id: string } }){
    const {id} = params;
    const productId = parseInt(id, 10);
    const products = getProducts();


    // 배열에서 id가 같은 상품 검색
    const product = products.find((item: Product) => item.id === productId);

    if (!product) {
        return NextResponse.json({ error: "상품을 찾을 수 없습니다." }, { status: 404 });
    }

    return NextResponse.json(product);
}