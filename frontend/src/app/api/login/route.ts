import { NextResponse } from "next/server";

export async function POST(request: Request) {
    const body = await request.json();

    const backendResponse = await fetch("http://localhost:8080/admin/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
        credentials: "include"
    });

    if (!backendResponse.ok) {
        return NextResponse.json({ error: "관리자 로그인 실패" }, { status: backendResponse.status });
    }
    // 관리자 메인 페이지로 리디렉션
    return NextResponse.redirect("http://localhost:3000/admin");
}
