import {
  Alert,
  Button,
  Grid,
  TextField,
  FormControl,
  FormControlLabel,
  FormLabel,
  Radio,
  RadioGroup,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { NAV_CLICK_ACTION } from "../../nav-actions";
import { register } from "../../connections/login-register";

export default function Register({ callback }) {
  const [alert, setAlert] = useState({});
  const [loading, setLoading] = useState(false);
  const [userDetails, setUserDetails] = useState({
    emailId: "",
    firstName: "",
    lastName: "",
    mobileNumber: "",
    gender: "male",
    password: "",
  });
  const [address, setAddress] = useState({
    addressLine1: "",
    addressLine2: "",
    city: "",
    pin: "",
    state: "",
  });

  const handleLoginClick = () =>
    callback ? callback(NAV_CLICK_ACTION.LOGIN) : null;

  const handleOnSubmit = (e) => {
    e.preventDefault();
    //validate passwod
    if (userDetails?.password.length < 5) {
      setAlert({
        severity: "error",
        message: "Password must be greater than or equal to 5 characters",
      });

      return;
    }

    setAlert({});

    //submit request for register

    const resCallback = {
      success: (res) => {
        // setLoading(false);
        // localStorage.setItem("user", res.data.data);

        setAlert({
          severity: "success",
          message: "Registered successfully",
        });

        setTimeout(handleLoginClick, 500);

        // handleLoginClick();
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

    setLoading(true);
    register({ userDetails, address }, resCallback);
  };

  return (
    <form onSubmit={handleOnSubmit}>
      <Grid container alignItems="center" justifyContent="center">
        <Grid
          style={{
            backgroundColor: "#ecedee",
            padding: "30px",
            borderRadius: "10px",
            textAlign: "center",
          }}
          item
          xs={12}
          sm={10}
          md={6}
          lg={6}
        >
          <Typography
            align="center"
            variant="h5"
            sx={{ paddingBottom: "10px" }}
          >
            REGISTER
          </Typography>

          {alert.message ? (
            <>
              <Alert severity={alert.severity}>{alert.message}</Alert> <br />
            </>
          ) : null}

          <TextField
            fullWidth
            value={userDetails?.emailId}
            onChange={(e) => {
              setUserDetails({ ...userDetails, emailId: e.target.value });
            }}
            label="Email"
            variant="outlined"
            type="email"
            required
          />
          <br />
          <br />

          <span
            style={{
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            <TextField
              fullWidth
              value={userDetails?.firstName}
              onChange={(e) => {
                setUserDetails({ ...userDetails, firstName: e.target.value });
              }}
              label="First Name"
              variant="outlined"
              type="text"
              required
            />

            <TextField
              fullWidth
              value={userDetails?.lastName}
              onChange={(e) => {
                setUserDetails({ ...userDetails, lastName: e.target.value });
              }}
              label="Last Name"
              variant="outlined"
              type="text"
              required
            />
          </span>

          <br />

          <TextField
            fullWidth
            value={userDetails?.mobileNumber}
            onChange={(e) => {
              setUserDetails({ ...userDetails, mobileNumber: e.target.value });
            }}
            label="Mobile Number"
            variant="outlined"
            type="number"
            required
          />

          <br />
          <br />

          <FormControl fullWidth>
            <FormLabel
              id="gender-radio-group-label"
              sx={{
                textAlign: "left",
              }}
            >
              Gender
            </FormLabel>
            <RadioGroup
              aria-labelledby="gender-radio-group-label"
              value={userDetails?.gender}
              name="radio-buttons-group"
              row
              onChange={(e) => {
                setUserDetails({ ...userDetails, gender: e.target.value });
              }}
            >
              <FormControlLabel value="male" control={<Radio />} label="Male" />
              <FormControlLabel
                value="female"
                control={<Radio />}
                label="Female"
              />
              <FormControlLabel
                value="other"
                control={<Radio />}
                label="Other"
              />
            </RadioGroup>
          </FormControl>

          <br />
          <br />

          <TextField
            fullWidth
            value={userDetails?.password}
            onChange={(e) => {
              setUserDetails({ ...userDetails, password: e.target.value });
            }}
            label="Password"
            variant="outlined"
            type="password"
            required
          />

          <br />
          <br />

          <Typography
            align="left"
            variant="subtitle2"
            sx={{ paddingBottom: "10px" }}
          >
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
            required
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
            required
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
            required
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
            required
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
            required
          />

          <br />
          <br />

          <Button
            fullWidth
            variant="contained"
            type="submit"
            disabled={loading}
          >
            {loading ? "Please wait" : "REGISTER"}
          </Button>

          <br />
          <br />

          <Button fullWidth variant="text" onClick={handleLoginClick}>
            Already registered? Login here
          </Button>
        </Grid>
      </Grid>
    </form>
  );
}
