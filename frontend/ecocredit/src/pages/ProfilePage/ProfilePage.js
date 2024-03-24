import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCoins } from "@fortawesome/free-solid-svg-icons";
import ProfileForm from "../../components/ProfileForm";
import Toast from "../../components/Toast/Toast";
import profilePicURL from "../../assets/images/profilePic.jpg";
import "./ProfilePage.css";
import Cookies from "js-cookie";

const ProfilePage = () => {
  const [profileData, setProfileData] = useState({
    id: "",
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    address: {
      street: "",
      city: "",
      province: "",
      postalCode: "",
      country: "",
    },
  });
  const [editMode, setEditMode] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastType, setToastType] = useState("");
  const [creditAmount, setCreditAmount] = useState(0);

  useEffect(() => {
    fetch(`${process.env.REACT_APP_BASE_URL}/users/getprofile`, {
      method: "GET",
      mode: "cors",
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => setProfileData(data))
      .catch((error) => console.error("Fetching profile data failed:", error));

    fetch(`${process.env.REACT_APP_BASE_URL}/wallet/getCredit`, {
      method: "GET",
      mode: "cors",
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setCreditAmount(data.creditAmount);
      })
      .catch((error) => console.error("Error fetching credit amount:", error));
  }, []);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setProfileData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleAddressChange = (event) => {
    const { name, value } = event.target;
    setProfileData((prevState) => ({
      ...prevState,
      address: {
        ...prevState.address,
        [name]: value,
      },
    }));
  };

  const handleSave = (event) => {
    event.preventDefault();
    fetch(`${process.env.REACT_APP_BASE_URL}/users/update-profile`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify(profileData),
    })
      .then((response) => {
        if (response.ok) {
          setToastMessage("Profile updated successfully");
          setToastType("success");
          setShowToast(true);
        } else {
          throw new Error("Failed to update profile");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        setToastMessage("Error updating profile");
        setToastType("error");
        setShowToast(true);
      })
      .finally(() => setEditMode(false));
  };

  return (
    <div>
      <Container className="min-vh-100 d-flex align-items-center justify-content-center bg-ec-light-green">
        <Row>
          <Col lg={3} className="mb-4 text-center">
            <Image
              src={profilePicURL}
              alt="Profile"
              roundedCircle
              style={{ width: "200px", height: "200px" }}
            />
            <h3 className="mt-4 text-ec-dark-green fw-bold fs-4">
              EcoCredits in Wallet
            </h3>
            <p className="text-ec-dark-green fw-bold fs-5">
              <FontAwesomeIcon icon={faCoins} className="fa-icon" />{" "}
              {creditAmount}
            </p>
          </Col>
          <Col lg={1}>
            <div className="profile-divider"></div>
          </Col>
          <ProfileForm
            profileData={profileData}
            handleInputChange={handleInputChange}
            handleAddressChange={handleAddressChange}
            handleSave={handleSave}
            editMode={editMode}
            setEditMode={setEditMode}
          />
        </Row>
      </Container>
      <Toast
        showToast={showToast}
        setShowToast={setShowToast}
        toastMessage={toastMessage}
        toastType={toastType}
      />
    </div>
  );
};

export default ProfilePage;
