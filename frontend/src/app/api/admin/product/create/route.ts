import {NextRequest, NextResponse} from "next/server";
import {ProductRequestDto} from "@/app/types/Product";

export async function POST(request: NextRequest) {
    try {
        // 요청 바디를 파싱
        const body: ProductRequestDto = await request.json();

        // 유효성 검사
        if (!body.name ||
            body.price <= 0 ||
            !body.quantity) {
            return NextResponse.json(
                {message: "유효하지 않은 데이터입니다."},
                {status: 400}
            );
        }


        const response = await fetch(`http://localhost:8080/admin/product/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${process.env.REACT_APP_SERVER_ID}`
            },
            body: JSON.stringify(body),
        });

        if (response.ok) {
            return NextResponse.json({
                message: "상품이 추가되었습니다.",
            });
        }
    } catch (error: any) {
        return NextResponse.json(
            {message: "상품 등록 실패", error: error.message},
            {status: 500}
        );
    }
}