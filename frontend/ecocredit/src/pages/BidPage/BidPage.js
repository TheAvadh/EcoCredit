import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Cookies from "js-cookie";
import CenterContainer from "../../components/CenterContainer";
import Toast from "../../components/Toast/Toast";
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

const rangeDifference = (currentPrice) => {
  if (currentPrice <= 20) {
    return 2;
  } else if (currentPrice <= 50) {
    return 5;
  } else if (currentPrice <= 100) {
    return 10;
  } else if (currentPrice <= 250) {
    return 25;
  } else if (currentPrice <= 500) {
    return 50;
  } else {
    return 100;
  }
};

const BidPage = () => {
  const { bidId } = useParams();
  const navigate = useNavigate();
  const [bidData, setBidData] = useState({
    id: "",
    date: "",
    waste_type: "",
    waste_weight: "",
    bid_amount: "",
    is_Active: "",
    highest_bid: "",
    user: {
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
    },
  });
  const [nextBid, setNextBid] = useState(0);
  const [customBidAmount, setCustomBidAmount] = useState("");
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastType, setToastType] = useState("");

  useEffect(() => {
    fetch(`${process.env.REACT_APP_BASE_URL}/recycler/place-bid/${bidId}`, {
      method: "GET",
      mode: "cors",
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setBidData(data);
        const nextBidValue =
          data.highest_bid + rangeDifference(data.highest_bid);
        setNextBid(nextBidValue);
      })
      .catch((error) =>
        console.error("Error fetching bid data failed:", error)
      );
  }, [bidId]);

  const handleSubmit = (event) => {
    event.preventDefault();
    const bidAmount = customBidAmount ? customBidAmount : nextBid;

    fetch(`${process.env.REACT_APP_BASE_URL}/recycler/raise-bid`, {
      method: "PUT",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
      body: JSON.stringify({
        bidId: bidId,
        newBidAmount: bidAmount,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        setToastMessage("Bid placed successfully!");
        setToastType("success");
        setShowToast(true);
        setTimeout(() => {
          navigate("/recycler/active-bids");
        }, 3000);
      })
      .catch((error) => {
        console.error("Error:", error);
        setToastMessage("Error placing bid");
        setToastType("error");
        setShowToast(true);
      });
  };

  return (
    <div>
      <CenterContainer title="Place a Bid" colSize={8}>
        <Form className="mt-5" onSubmit={handleSubmit}>
          <Row>
            <Col xxl={4} className="mb-4 text-center">
              <Image
                rounded
                src={imageMap[bidData.waste_type]}
                alt={bidData.waste_type}
                style={{ width: "338px", height: "190px" }}
              />
            </Col>
            <Col xxl={1}></Col>
            <Col xxl={7}>
              <dl className="row text-end text-ec-dark-green fs-5 pb-4">
                <dt className="col-7">Waste Type</dt>
                <dd className="col-5">
                  {bidData.waste_type.charAt(0).toUpperCase() +
                    bidData.waste_type.slice(1)}
                </dd>

                <dt className="col-7">Weight</dt>
                <dd className="col-5">{bidData.waste_weight} KG</dd>

                <dt className="col-7">Current Highest Bid</dt>
                <dd className="col-5">{bidData.highest_bid} CAD</dd>

                <dt className="col-7">Starting Bid</dt>
                <dd className="col-5">{bidData.bid_amount} CAD</dd>
              </dl>
              <Row className="mt-5">
                <Col
                  lg={7}
                  className="d-flex align-items-center text-ec-dark-green"
                >
                  <p className="fs-5 fw-bold">Next Bid Amount: ${nextBid}</p>
                </Col>
                <Col lg={5} className="d-grid pb-3">
                  <Button
                    variant="ec-dark-green"
                    size="lg"
                    className="p-3"
                    onClick={(e) => {
                      e.preventDefault();
                      setCustomBidAmount(nextBid.toString());
                      handleSubmit(e);
                    }}
                  >
                    Bid for {nextBid}$
                  </Button>
                </Col>
              </Row>
              <Row>
                <Col lg={7}>
                  <Form.Floating className="mb-3">
                    <Form.Control
                      id="floatingInputCustom"
                      type="number"
                      placeholder="Bid Amount"
                      min={nextBid}
                      value={customBidAmount}
                      onChange={(e) => setCustomBidAmount(e.target.value)}
                    />
                    <label htmlFor="floatingInputCustom">
                      Enter Custom Bid Amount
                    </label>
                  </Form.Floating>
                </Col>
                <Col lg={5} className="d-grid text-end mb-3">
                  <Button variant="ec-dark-green" type="submit" size="lg">
                    Place Bid
                  </Button>
                </Col>
              </Row>
            </Col>
          </Row>
        </Form>
      </CenterContainer>
      <Toast
        showToast={showToast}
        setShowToast={setShowToast}
        toastMessage={toastMessage}
        toastType={toastType}
      />
    </div>
  );
};

export default BidPage;
