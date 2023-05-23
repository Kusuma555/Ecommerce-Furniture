import { Button } from "@mui/material";
import { Router, useRouter } from "next/router";
import React from "react";

export default function BackButton() {
  const router = useRouter();

  const handleGoBack = () => router.back();

  return (
    <Button variant="text" onClick={handleGoBack}>
      {"< Back"}
    </Button>
  );
}
