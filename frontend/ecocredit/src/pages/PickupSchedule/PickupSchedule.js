import React, { useState } from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import CenterContainer from "../../components/CenterContainer";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import logoUrl from "../../assets/images/recycle.png";
import { ToastContainer, toast } from "react-toastify";
import Cookies from "js-cookie";
import "react-toastify/dist/ReactToastify.css";

const PickupSchedule = () => {
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");
  const [categories, setCategories] = useState({
    biodegradable: false,
    plastics: false,
    glass: false,
    electronics: false,
    paper: false,
    mixed: false,
  });

  const handleDateChange = (e) => {
    setDate(e.target.value);
  };

  const handleTimeChange = (e) => {
    setTime(e.target.value);
  };

  const handleCategoryChange = (e) => {
    const { name, checked } = e.target;

    setCategories((prevCategories) => ({
      ...prevCategories,
      [name]: checked,
    }));
  };

  const handleConfirm = (e) => {
    e.preventDefault();

    const selectedCategories = Object.keys(categories).filter(
      (category) => categories[category]
    );

    const wastes = selectedCategories.map((c) => {
      return {
        category: c,
        waste: 0, // set weights to 0 for now
      };
    });

    const pickupData = {
      dateTime: `${date}T${time}`,
      wastes: wastes,
    };

    fetch(`${process.env.REACT_APP_BASE_URL}/pickups/schedule`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
      body: JSON.stringify(pickupData),
    })
      .then(async (response) => {
        if (response.ok) {
          console.log("Success:", response);
          showToastMessagePickupScheduled();
          const data = await response.json();
          window.location = data.checkoutUrl;
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  const handleCancel = () => {
    setDate("");
    setTime("");
    setCategories({
      biodegradable: false,
      plastics: false,
      glass: false,
      electronics: false,
      paper: false,
      mixed: false,
    });
    document.getElementById("date").value = "";
    document.getElementById("time").value = "";

    document
      .querySelectorAll('input[name="biodegradable"]')
      .forEach((checkbox) => {
        checkbox.checked = false;
      });

    document.querySelectorAll('input[name="plastics"]').forEach((checkbox) => {
      checkbox.checked = false;
    });

    document.querySelectorAll('input[name="glass"]').forEach((checkbox) => {
      checkbox.checked = false;
    });

    document
      .querySelectorAll('input[name="electronics"]')
      .forEach((checkbox) => {
        checkbox.checked = false;
      });

    document.querySelectorAll('input[name="paper"]').forEach((checkbox) => {
      checkbox.checked = false;
    });

    document.querySelectorAll('input[name="mixed"]').forEach((checkbox) => {
      checkbox.checked = false;
    });

    showToastMessagePickupCanceled();
  };

  const showToastMessagePickupScheduled = () => {
    toast.success("Your Pickup has been scheduled!", {
      position: "top-center",
      autoClose: 3000,
    });
  };

  const showToastMessagePickupCanceled = () => {
    toast.error("Canceled!", {
      position: "top-center",
      autoClose: 3000,
    });
  };

  return (
    <CenterContainer title="Schedule Garbage Collection">
      <Row>
        <Col>
          <Form>
            <Form.Group controlId="formDate">
              <FloatingLabel controlId="date" label="Date" className="mb-3">
                <Form.Control
                  name="date"
                  type="date"
                  onChange={handleDateChange}
                  required
                />
              </FloatingLabel>
            </Form.Group>

            <Form.Group controlId="formTime">
              <FloatingLabel controlId="time" label="Time" className="mb-3">
                <Form.Control
                  name="time"
                  type="time"
                  onChange={handleTimeChange}
                  required
                />
              </FloatingLabel>
            </Form.Group>

            <Form.Group controlId="categories" className="mb-3 text-center">
              <div className="d-flex align-items-center justify-content-center">
                <div className="me-2">
                  <img
                    src={logoUrl}
                    alt="Waste Categories"
                    width="30"
                    height="30"
                  />
                </div>
                <div>
                  <h6 className="mb-0">Categories</h6>
                </div>
              </div>
              <Row className="justify-content-center mt-3">
                <Col md>
                  <Form.Check
                    type="switch"
                    id="biodegradable-switch"
                    name="biodegradable"
                    value="biodegradable"
                    label="Biodegradable"
                    onChange={handleCategoryChange}
                  />
                </Col>
                <Col md>
                  <Form.Check
                    type="switch"
                    id="plastics-switch"
                    name="plastics"
                    value="plastics"
                    label="Plastics"
                    onChange={handleCategoryChange}
                  />
                </Col>
                <Col md>
                  <Form.Check
                    type="switch"
                    id="glass-switch"
                    name="glass"
                    value="glass"
                    label="Glass"
                    onChange={handleCategoryChange}
                  />
                </Col>
              </Row>
              <Row className="justify-content-center mt-3">
                <Col md>
                  <Form.Check
                    type="switch"
                    id="electronics-switch"
                    name="electronics"
                    value="electronics"
                    label="Electronics"
                    onChange={handleCategoryChange}
                  />
                </Col>
                <Col md>
                  <Form.Check
                    type="switch"
                    id="paper-switch"
                    name="paper"
                    value="paper"
                    label="Paper"
                    onChange={handleCategoryChange}
                  />
                </Col>
                <Col md>
                  <Form.Check
                    type="switch"
                    id="mixed-switch"
                    name="mixed"
                    value="mixed"
                    label="Mixed"
                    onChange={handleCategoryChange}
                  />
                </Col>
              </Row>
            </Form.Group>

            <div className="d-grid">
              <Row className="g-2 text-center">
                <Col md>
                  <Button
                    onClick={handleConfirm}
                    variant="ec-dark-green"
                    type="submit"
                    className="w-75 mx-auto mt-4 mb-2"
                    size="xl"
                  >
                    Confirm
                  </Button>
                </Col>
                <Col md>
                  <Button
                    onClick={handleCancel}
                    variant="ec-dark-green"
                    type="button"
                    className="w-75 mx-auto mt-4 mb-2"
                    size="xl"
                  >
                    Cancel
                  </Button>
                </Col>
              </Row>
            </div>
          </Form>
        </Col>
      </Row>

      <ToastContainer
        position="top-center"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </CenterContainer>
  );
};

export default PickupSchedule;
