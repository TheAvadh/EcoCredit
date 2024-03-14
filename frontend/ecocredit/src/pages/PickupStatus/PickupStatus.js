import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Table from "react-bootstrap/Table";
import Cookies from "js-cookie";
import "../../components/PickupScheduleContainer/PickupScheduleContainer.css";

const PickupStatus = () => {
  const [pickups, setPickups] = useState([]);

  useEffect(() => {
    fetch(
      `${process.env.REACT_APP_BASE_URL}/pickups/${localStorage.getItem(
        "userId"
      )}`,
      {
        method: "GET",
        mode: "cors",
        headers: {
          Authorization: `Bearer ${Cookies.get("token")}`,
          Accept: "application/json",
        },
      }
    )
      .then((response) => response.json())
      .then((data) => setPickups(data))
      .catch((error) =>
        console.error("Fetching pickup status data failed:", error)
      );
  }, []);

  return (
    <Container fluid className="background-image">
      <Row className="d-flex justify-content-center align-items-center">
        <Col lg={6} className="mt-5">
          <div className="shadow-lg rounded-4 bg-ec-grey p-4">
            <h1 className="text-center text-ec-dark-green p-2">
              Pickup Status
            </h1>
            <Table
              striped
              bordered
              hover
              variant="ec-grey"
              className="table-custom mb-0"
            >
              <thead>
                <tr>
                  <th>#</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {pickups.map((pickup) => (
                  <tr key={pickup.pickupId}>
                    <td>{pickup.pickupId}</td>
                    <td>{pickup.pickupDate}</td>
                    <td>{pickup.pickupTime}</td>
                    <td>{pickup.pickupStatus}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default PickupStatus;