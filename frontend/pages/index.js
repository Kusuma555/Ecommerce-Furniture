import { useEffect } from "react";
import App from "../App";

export default function index() {
  useEffect(() => {
    document.title = "Buy Sell Trade";
  }, []);

  return <App />;
}
