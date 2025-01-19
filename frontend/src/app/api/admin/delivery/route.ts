import {NextRequest, NextResponse} from "next/server";

// PUT /admin/delivery
export async function PUT(request: NextRequest) {
    try {
        const body = await request.json();

        if (!Array.isArray(body.id) || body.id.length === 0) {
            return NextResponse.json(
                { message: "ID 없음" },
                { status: 400 }
            );
        }
        
        const response = await fetch(`http://localhost:8080/admin/delivery`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body),
        });

        if (!response.ok) {
            throw new Error('배송 정보 수정 에러');
        }
        

        return NextResponse.json({
            message: "배송 정보 수정 완료."
        });
    } catch (error: any) {
        throw(error);
    }
}