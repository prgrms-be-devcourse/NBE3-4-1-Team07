import {NextRequest, NextResponse} from "next/server";
import {Product} from "@/app/types/Product";

export async function POST(request: NextRequest) {
    try {
        // 요청 바디를 파싱
        const body: Product = await request.json();

        // 유효성 검사
        if (!body.name ||
            !body.price ||
            !body.quantity ||
            !body.description ||
            !body.imgPath) {
            return NextResponse.json(
                {message: "유효하지 않은 데이터입니다."},
                {status: 400}
            );
        }

        const response = await fetch(`/admin/product/${body.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body),
        });

        // 응답 상태 확인
        if (response.ok) {
            console.log("상품 정보가 업데이트되었습니다.");
        } else {
            console.error("상품 정보 업데이트 실패", await response.text());
        }

        // 성공 메시지 반환
        return NextResponse.json({
            message: "상품 정보 업데이트 요청이 처리되었습니다.",
            product: body,
        });
    } catch (error: any) {
        return NextResponse.json(
            {message: "상품 등록 실패", error: error.message},
            {status: 500}
        );
    }
}