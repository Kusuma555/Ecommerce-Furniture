import { TextField, Box, Button, Divider, FormControl } from "@mui/material";
import React, { useState } from "react";

export default function SearchBar() {
  const [searchQuery, setSearchQuery] = useState("");
  const handleSearchQueryChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const style = {
    display: "flex",
    padding: "10px",
  };
  return (
    <form>
      <Box sx={style}>
        <TextField
          fullWidth
          value={searchQuery}
          onChange={handleSearchQueryChange}
          label="Search"
          variant="filled"
          type="text"
          placeholder="Chair"
          required
        />
        <Divider orientation="vertical" />
        <Button variant="outlined" type="submit">
          Search
        </Button>
      </Box>
    </form>
  );
}
