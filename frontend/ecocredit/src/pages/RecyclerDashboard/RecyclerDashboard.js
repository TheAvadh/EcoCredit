import React from "react";
import { Route } from "react-router-dom";
import ProfilePage from "../ProfilePage/ProfilePage";
import Dashboard from "../../components/Dashboard";
import {
  faCommentsDollar,
  faHandHoldingDollar,
  faIdCard,
} from "@fortawesome/free-solid-svg-icons";
import ActiveBids from "../ActiveBids/ActiveBids";
import BidPage from "../BidPage/BidPage";
import ViewMyBids from "../ViewMyBids/ViewMyBids";

const RecyclerDashboard = () => {
  const tabs = [
    {
      label: "Active Bids",
      to: "/recycler/active-bids",
      path: "/active-bids",
      icon: faCommentsDollar,
      component: <ActiveBids />,
    },
    {
      label: "View My Bids",
      to: "/recycler/view-my-bids",
      path: "/view-my-bids",
      icon: faHandHoldingDollar,
      component: <ViewMyBids />,
    },
    {
      label: "Profile",
      to: "/profile-page",
      path: "/profile-page",
      icon: faIdCard,
      component: <ProfilePage />,
    },
  ];

  return (
    <Dashboard tabs={tabs}>
      <Route path="active-bids/:bidId" element={<BidPage />} />
    </Dashboard>
  );
};

export default RecyclerDashboard;
