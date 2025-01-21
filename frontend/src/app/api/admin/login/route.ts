import { NextResponse } from "next/server";

export async function GET() {
    return NextResponse.json(
        { message: "Admin login page" },
        { status: 200 }
    );
}

export async function POST(request: Request) {
    try {
        const body = await request.json();
        const { username, password } = body;

        // 서버로 로그인 요청
        const response = await fetch("http://localhost:8080/admin/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ username, password }),
        });

        const data = await response.text(); // 문자열 응답을 받음

        if (!response.ok) {
            return NextResponse.json(
                { message: data },
                { status: 401 }
            );
        }

        // 로그인 성공 시
        return NextResponse.json(
            { message: data },
            {
                status: 200,
                headers: {
                    'Set-Cookie': `adminLoggedIn=true; Path=/; HttpOnly; Secure; SameSite=Strict`
                }
            }
        );

    } catch (error) {
        return NextResponse.json(
            { message: "서버 오류" },
            { status: 500 }
        );
    }
} 