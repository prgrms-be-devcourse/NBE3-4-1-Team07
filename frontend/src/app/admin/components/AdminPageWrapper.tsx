"use client";
import { useSession } from "next-auth/react"
import { useRouter } from "next/navigation"
import React from "react"
import Page from "@/app/login/page";

interface AdminPageWrapperProps {
    children: React.ReactNode
}

export default function AdminPageWrapper({ children }: AdminPageWrapperProps) {
    if (status === "loading") {
        return <div>Loading...</div>
    }

    if (!session) {
        return <Page />
    }

    return <>{children}</>
}
