import {NextRequest, NextResponse} from "next/server";


// POST /admin/product
export async function POST(request: NextRequest) {
    try {

        const resposne = await fetch("http://localhost:8080/admin/product");


        return NextResponse.json({
            message: "새로운 상품이 추가되었습니다.",
        });
    } catch (error) {
        return NextResponse.json(
            { message: "상품 등록 실패", error: error.message },
            { status: 500 }
        );
    }
}

