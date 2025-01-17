import {getProducts, saveProducts} from "@/app/api/main/productList/route";
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

// PUT /api/admin/product/${id}
export async function PUT(request: NextRequest, { params }: { params: { id: string } }) {
    try {
        const { id } = params;
        const productId = parseInt(id, 10);
        const products = getProducts();

        const updatedProduct: Product = await request.json();
        const productIndex = products.findIndex((item: Product) => item.id === productId);

        if (productIndex === -1) {
            return NextResponse.json({ error: "상품을 찾을 수 없습니다." }, { status: 404 });
        }

        // 상품 데이터 업데이트
        products[productIndex] = {
            ...products[productIndex],
            ...updatedProduct,
        };

        // 변경된 상품 리스트 저장 (가상의 함수 saveProducts 사용)
        saveProducts(products);

        return NextResponse.json(products[productIndex]);
    } catch (error) {
        console.error("상품 업데이트 중 오류 발생:", error);
        return NextResponse.json({ error: "상품 업데이트에 실패했습니다." }, { status: 500 });
    }
}