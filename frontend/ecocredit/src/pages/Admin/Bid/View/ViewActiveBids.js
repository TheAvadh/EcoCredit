import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Table from "react-bootstrap/Table";
import moment from "moment";
import Cookies from "js-cookie";
import "../Styling/Bids.css";

const ViewActiveBids = () => {
    const [bids, setBids] = useState([
        {
            "waste": {
                "id": "",
                "category": {
                    "id": "",
                    "value": ""
                },
                "weight": ""
            },
            "base_price": "",
            "top_bid_amount": "",
            "user": {
                "firstName": "",
                "lastName": ""
            },
            "date": "",
            "sold": "",
            "id": "",
            "_active": ""
        }
    ]);

    useEffect(() => {
        fetch(
          `${process.env.REACT_APP_BASE_URL}/admin/allactivebids`,
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
          .then((data) => {
              const sortedBids = [...data].sort((a, b) => moment(b.date).unix() - moment(a.date).unix());
              setBids(sortedBids);
          })
          .catch((error) => console.error("Fetching bids failed:", error));
      }, []);


   return (
        <Container fluid className="background-image">
            <Row className="d-flex justify-content-center align-items-center">
                <Col lg={12} className="mt-5">
                    <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
                        <h1 className="text-center text-ec-dark-green p-2">
                            View Active Bids
                        </h1>
                        <div className="bids-table-wrapper">
                            <Table
                            bordered
                            hover
                            variant="ec-grey"
                            className="table-custom mb-0"
                            >
                                <thead>
                                    <tr>
                                        <th>Waste ID</th>
                                        <th>Category</th>
                                        <th>Weight (kg)</th>
                                        <th>Base Price ($)</th>
                                        <th>Top Bid Amount ($)</th>
                                        <th>Winner</th>
                                        <th>DateTime</th>
                                        <th>Bid Status</th>
                                        <th>Payment Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {bids.map((bid) => (
                                        <tr key={bid.id}>
                                            <td>{bid.waste.id}</td>
                                            <td>{bid.waste.category.value}</td>
                                            <td>{bid.waste.weight}</td>
                                            <td>{bid.base_price}</td>
                                            <td>{bid.top_bid_amount}</td>
                                            <td>{bid.user ? bid.user.firstName + " " + bid.user.lastName : "TBD"}</td>
                                            <td>{bid.date}</td>
                                            <td>{bid._active ? "active": "inactive"}</td>
                                            <td>{bid.sold ? "paid" : "not paid"}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>
                        </div>
                    </div>
                </Col>
            </Row>
        </Container>
   );
};


export default ViewActiveBids;