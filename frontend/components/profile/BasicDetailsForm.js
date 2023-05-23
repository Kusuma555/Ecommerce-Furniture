import {
  Alert,
  Button,
  TextField,
  FormControl,
  FormControlLabel,
  FormLabel,
  Radio,
  RadioGroup,
  Typography,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import {
  getUserDetails,
  updateUserDetails,
} from "../../connections/user-details";

export default function BasicDetailsForm({ authToken, userId }) {
  const [alert, setAlert] = useState({});
  const [loading, setLoading] = useState(false);
  const [editDetails, setEditDetails] = useState(false);
  const [userDetails, setUserDetails] = useState({
    emailId: "",
    firstName: "",
    lastName: "",
    mobileNumber: "",
    gender: "male",
    password: "",
  });

  const handleUserDetailsUpdate = (e) => {
    e.preventDefault();
    setLoading(true);

    setAlert({});

    const resCallback = {
      success: (res) => {
        setLoading(false);
        setUserDetails({ ...res.data.data });
        setAlert({
          severity: "success",
          message: "Updated successfully",
        });

        setEditDetails(false);
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

    updateUserDetails({ authToken, userId, userDetails }, resCallback);
  };

  useEffect(() => {
    setLoading(true);

    const resCallback = {
      success: (res) => {
        setUserDetails({ ...res.data.data });
        setLoading(false);
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

    getUserDetails({ authToken, userId }, resCallback);
  }, []);

  return (
    <>
      {alert.message ? (
        <>
          <Alert severity={alert.severity}>{alert.message}</Alert> <br />
        </>
      ) : null}
      <form onSubmit={handleUserDetailsUpdate}>
        <Typography align="left" variant="h5" sx={{ paddingBottom: "10px" }}>
          Personal Details
        </Typography>

        <TextField
          fullWidth
          value={userDetails?.emailId}
          onChange={(e) => {
            setUserDetails({ ...userDetails, emailId: e.target.value });
          }}
          label="Email"
          variant="outlined"
          type="email"
          disabled={true}
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
            disabled={!editDetails}
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
            disabled={!editDetails}
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
          disabled={!editDetails}
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
            <FormControlLabel
              disabled={!editDetails}
              value="male"
              control={<Radio />}
              label="Male"
            />
            <FormControlLabel
              disabled={!editDetails}
              value="female"
              control={<Radio />}
              label="Female"
            />
            <FormControlLabel
              disabled={!editDetails}
              value="other"
              control={<Radio />}
              label="Other"
            />
          </RadioGroup>
        </FormControl>

        <br />
        <br />

        {editDetails ? (
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
          color={editDetails ? "error" : "success"}
          disabled={loading}
          onClick={(e) => {
            setEditDetails(!editDetails);
          }}
        >
          {loading ? "Please wait" : editDetails ? "Cancel" : "Edit Details"}
        </Button>
      </form>
    </>
  );
}
