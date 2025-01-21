import {NextRequest, NextResponse} from "next/server";
import {ProductRequestDto} from "@/app/types/Product";

export async function POST(request: NextRequest) {
    try {
        const formData = await request.formData();


        const name = formData.get("name")?.toString() || "";
        const price = parseFloat(formData.get("price")?.toString() || "0");
        const quantity = parseInt(formData.get("quantity")?.toString() || "0", 10);
        const description = formData.get("description")?.toString() || "";
        const image = formData.get("image");

        // 유효성 검사
        if (!name || price <= 0 || quantity <= 0) {
            return NextResponse.json(
                {message: "유효하지 않은 데이터입니다."},
                {status: 400}
            );
        }

        const response = await fetch(`http://localhost:8080/admin/product/create`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${process.env.REACT_APP_SERVER_ID}`
            },
            body: formData,
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