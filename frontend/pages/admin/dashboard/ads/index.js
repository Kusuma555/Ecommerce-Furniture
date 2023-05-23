import { Container } from "@mui/system";
import { useRouter } from "next/router";
import React, { useContext, useEffect } from "react";
import AdList from "../../../../components/admin/ads/AdList";
import BackButton from "../../../../components/BackButton";
import { AuthContext } from "../../../../contexts/AuthContext";

export default function index() {
  const { authToken } = useContext(AuthContext);
  const router = useRouter();

  useEffect(() => {
    document.title = "Adim: Ads";
    if (!authToken) router.push(`${router.basePath}/admin`);
  }, [authToken]);

  return (
    <>
      {authToken ? (
        <Container>
          <BackButton />
          <AdList />
        </Container>
      ) : null}
    </>
  );
}
