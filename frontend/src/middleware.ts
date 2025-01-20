import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'


export async function middleware(request: NextRequest) {
    const sessionId = request.cookies.get('JSESSIONID')?.value;
    const baseUrl = 'http://localhost:3000';

    // 세션이 없으면
    if (!sessionId) {
        return NextResponse.redirect(`${baseUrl}/login`);
    }else {
        // 세션 유효성 검증
        const sessionCheckResponse = await fetch('http://localhost:8080/admin/checkSession', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({sessionId}),
            credentials: 'include', // 쿠키가 포함된 요청
        });

        if (!sessionCheckResponse.ok) {
            // 세션이 유효하지 않으면 로그인 페이지로 리디렉션
            return NextResponse.redirect(`${baseUrl}/login`);
        }
    }
    return NextResponse.next();
}

export const config = {
    matcher: [
        '/admin/:path*',
    ],
};

