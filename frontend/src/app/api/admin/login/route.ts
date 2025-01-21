import { NextResponse } from "next/server";

export async function POST(request: Request) {
    try {
        const body = await request.json();
        const { username, password } = body;

        console.log('로그인 시도:', { username, password });

        // Spring 서버로 요청 전달
        const response = await fetch("http://localhost:8080/api/admin/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                'Accept': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        const responseText = await response.text();
        console.log('서버 응답:', responseText);

        if (!response.ok) {
            return NextResponse.json(
                { message: responseText },
                { status: response.status }
            );
        }

        return NextResponse.json(
            { message: responseText },
            { status: 200 }
        );

    } catch (error) {
        console.error('로그인 에러:', error);
        return NextResponse.json(
            { message: "서버 오류" },
            { status: 500 }
        );
    }
} 