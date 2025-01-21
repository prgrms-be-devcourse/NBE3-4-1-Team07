import {NextRequest, NextResponse} from "next/server";
import {Product} from "@/app/types/Product";


// GET /api/admin/product/${id}
export async function GET(request: NextRequest, { params }: { params: { id: number } }) {
    const { id } = await params;
    const res = await fetch(`http://localhost:8080/admin/product/${id}`);
    const product: Product = await res.json();

    if(!res.ok){
        if (!res.ok) {
            throw new Error("주문 목록 조회 실패");
        }
    }
    return NextResponse.json(product);
}

// PUT /admin/product/${id}
export async function PUT(request: NextRequest, { params }: { params: { id: number } }) {
    try {
        const {id} = await params;
        const body = await request.json();
        //const formData = new FormData();

        // formData.append("productReqDto", JSON.stringify({
        //     name: body.name,
        //     price: body.price,
        //     quantity: body.quantity,
        // }));
        //
        // formData.append("file", body.image); // 이미지 파일 추가
        console.log(body);
        const response = await fetch(`http://localhost:8080/admin/product/${id}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json', // 헤더 추가
                },
                body: JSON.stringify({
                    name: body.name,
                    price: body.price,
                    quantity: body.quantity,
                }),
            });

        if (!response.ok) {
            throw new Error('상품 업데이트에 실패했습니다.');
        }

        return NextResponse.json({
            message: "상품 업데이트 성공",
            status: 200
        });
    } catch (error) {
        console.error("상품 업데이트 중 오류 발생:", error);
        return NextResponse.json({ error: "상품 업데이트에 실패했습니다." }, { status: 500 });
    }
}

// DELETE /admin/product
export async function DELETE(request: NextRequest) {
    try {
        const url = new URL(request.url);
        const id = url.pathname.split("/").pop();

        const response = await fetch(`http://localhost:8080/admin/product/${id}`, {
            method: "DELETE",
        });

        if (!response.ok) {
            throw new Error("상품 삭제 실패");
        }
        return NextResponse.json({
            message: "상품 삭제 완료.",
        });
    } catch (error) {
        return NextResponse.json(
            { message: "상품 삭제 실패", error: error.message },
            { status: 500 }
        );
    }
}