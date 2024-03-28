import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Badge from "react-bootstrap/Badge";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import Cookies from "js-cookie";
import biodegradableURL from "../../assets/images/biodegradable.jpg";
import electronicsURL from "../../assets/images/electronics.jpg";
import glassURL from "../../assets/images/glass.jpg";
import mixedURL from "../../assets/images/mixed.jpg";
import paperURL from "../../assets/images/paper.jpg";
import plasticsURL from "../../assets/images/plastics.jpg";

const imageMap = {
  biodegradable: biodegradableURL,
  electronics: electronicsURL,
  glass: glassURL,
  mixed: mixedURL,
  paper: paperURL,
  plastics: plasticsURL,
};

const ViewMyBids = () => {
  const navigate = useNavigate();
  const [bids, setBids] = useState([]);

  useEffect(() => {
    fetch(`${process.env.REACT_APP_BASE_URL}/recycler/view-my-bids`, {
      method: "GET",
      mode: "cors",
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        const parsedBids = data
          .map((bid) => ({
            ...bid,
            date: moment(bid.date, "YYYYMMDDHHmm").toISOString(),
          }))
          .sort((a, b) => moment(b.date).unix() - moment(a.date).unix());
        setBids(parsedBids);
      })
      .catch((error) => console.error("Failed to fetch bids:", error));
  }, []);

  const redirectToBidPage = (bidId) => {
    navigate(`/recycler/active-bids/${bidId}`);
  };

  return (
    <Container fluid className="background-image">
      <Row xs={1} sm={2} lg={3} className="p-4">
        {bids.map((bid) => (
          <Col key={bid.id} className="d-flex g-3">
            <Card className="shadow-lg flex-fill rounded-4 bg-ec-grey text-ec-dark-green d-flex flex-column">
              <Card.Img variant="top" src={imageMap[bid.waste_type]} />
              <Card.Body className="d-flex flex-column">
                <Card.Title className="fs-3 fw-bold">
                  {bid.waste_type.charAt(0).toUpperCase() +
                    bid.waste_type.slice(1)}
                </Card.Title>
                <dl className="row">
                  <dt className="col-sm-5">Weight</dt>
                  <dd className="col-sm-7">{bid.waste_weight} KG</dd>

                  <dt className="col-sm-5">Current Highest Bid</dt>
                  <dd className="col-sm-7">{bid.bid.top_bid_amount} CAD</dd>

                  <dt className="col-sm-5">Starting Bid</dt>
                  <dd className="col-sm-7">{bid.bid.base_price} CAD</dd>

                  <dt className="col-sm-5">My Bid Amount</dt>
                  <dd className="col-sm-7">{bid.bid_amount} CAD</dd>

                  <dt className="col-sm-5">Status</dt>
                  <dd className="col-sm-7">
                    <Badge
                      bg={bid.is_Active ? "ec-dark-green" : "ec-light-green"}
                      className={bid.is_Active ? "" : "text-ec-dark-green"}
                    >
                      {bid.is_Active ? "Active" : "Completed"}
                    </Badge>
                  </dd>
                </dl>
                {bid.is_Active && (
                  <div className="text-end">
                    <Button
                      variant="ec-dark-green"
                      onClick={() => redirectToBidPage(bid.bid.id)}
                    >
                      Place a Bid
                    </Button>
                  </div>
                )}
              </Card.Body>
              <Card.Footer>
                <small className="text-muted">
                  Placed Bid On: {moment(bid.date).format("LLL")}
                </small>
              </Card.Footer>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default ViewMyBids;
