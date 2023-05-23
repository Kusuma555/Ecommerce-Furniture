import { Button, TextField, Typography, Alert } from "@mui/material";
import React, { useState, useEffect } from "react";
import {
  getAddress,
  saveAddress,
  updateAddress,
} from "../../connections/address";

export default function AddressForm({ authToken, userId }) {
  const [alert, setAlert] = useState({});
  const [loading, setLoading] = useState(false);
  const [editAddress, setEditAddress] = useState(false);
  const [save, setSave] = useState(false);

  const [address, setAddress] = useState({
    addressLine1: "",
    addressLine2: "",
    city: "",
    pin: "",
    state: "",
  });

  const handleAddressUpdate = (e) => {
    e.preventDefault();
    setLoading(true);

    setAlert({});

    const resCallback = {
      success: (res) => {
        setSave(false);
        setLoading(false);
        setAddress({ ...res.data.data });
        // console.log(res.data.data);

        setAlert({
          severity: "success",
          message: "Updated successfully",
        });

        setEditAddress(false);
      },
      error: (error) => {
        const msg = error?.response?.data?.message;
        setAlert({
          severity: "error",
          message: msg ? msg : error.message,
        });
        setLoading(false);
      },
    };

    // console.log(save);

    if (save) saveAddress({ userId, address }, resCallback);
    else
      updateAddress(
        { authToken, userId, addressId: address.addressId, address },
        resCallback
      );
  };

  useEffect(() => {
    setLoading(true);

    const resCallback = {
      success: (res) => {
        setAddress({ ...res.data.data[0] });
        setSave(res.data.data.length === 0);
        setLoading(false);
        // console.log(res.data.data);
        // setTimeout(() => {
        //   setLoading(false);
        // }, 2000);
      },
      error: (error) => {
        const msg = error?.response?.data?.message;
        setAlert({
          severity: "error",
          message: msg ? msg : error.message,
        });
        setLoading(false);
      },
    };

    getAddress({ authToken, userId }, resCallback);
  }, []);

  return (
    <>
      {alert.message ? (
        <>
          <Alert severity={alert.severity}>{alert.message}</Alert> <br />
        </>
      ) : null}
      <form onSubmit={handleAddressUpdate}>
        <Typography align="left" variant="h5" sx={{ paddingBottom: "10px" }}>
          Address
        </Typography>

        <TextField
          fullWidth
          value={address?.addressLine1}
          onChange={(e) => {
            setAddress({ ...address, addressLine1: e.target.value });
          }}
          label="Address Line 1"
          variant="outlined"
          type="text"
          disabled={!editAddress}
        />

        <br />
        <br />

        <TextField
          fullWidth
          value={address?.addressLine2}
          onChange={(e) => {
            setAddress({ ...address, addressLine2: e.target.value });
          }}
          label="Address Line 2"
          variant="outlined"
          type="text"
          disabled={!editAddress}
        />

        <br />
        <br />

        <TextField
          fullWidth
          value={address?.city}
          onChange={(e) => {
            setAddress({ ...address, city: e.target.value });
          }}
          label="City"
          variant="outlined"
          type="text"
          disabled={!editAddress}
        />

        <br />
        <br />

        <TextField
          fullWidth
          value={address?.state}
          onChange={(e) => {
            setAddress({ ...address, state: e.target.value });
          }}
          label="State"
          variant="outlined"
          type="text"
          disabled={!editAddress}
        />

        <br />
        <br />

        <TextField
          fullWidth
          value={address?.pin}
          onChange={(e) => {
            setAddress({ ...address, pin: e.target.value });
          }}
          label="PIN"
          variant="outlined"
          type="text"
          disabled={!editAddress}
        />

        <br />
        <br />
        {editAddress ? (
          <Button
            fullWidth
            sx={{
              marginBottom: "5px",
            }}
            variant="contained"
            type="submit"
            disabled={loading}
          >
            {loading ? "Please wait" : "Save"}
          </Button>
        ) : null}

        <Button
          fullWidth
          variant="contained"
          color={editAddress ? "error" : "success"}
          disabled={loading}
          onClick={(e) => {
            setEditAddress(!editAddress);
          }}
        >
          {loading ? "Please wait" : editAddress ? "Cancel" : "Edit Details"}
        </Button>
      </form>
    </>
  );
}
