import React from "react";
import { Container } from "@mui/system";

export default function Footer() {
  return (
    <footer
      style={{
        marginTop: "20px",
        textAlign: "center",
        padding: "10px",
        // position: "absolute",
        bottom: 0,
        width: "100%",
        height: "60px",
        // background: "#6cf",
        // background: "#cacaca",
      }}
    >
      <hr />
      Buy Sell Trade &copy;
    </footer>
  );
}
