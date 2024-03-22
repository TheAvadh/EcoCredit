import Dashboard from "../../../components/Dashboard";
import {
    faCalendarCheck,
    faCar,
    faCheck,
    faPenToSquare,
    faTrash
  } from "@fortawesome/free-solid-svg-icons";
import ScheduledPickups from "../Pickup/Scheduled/ScheduledPickups";
import CompletedPickups from "../Pickup/Completed/CompletedPickups";
import CreateBid from "../Bid/Create/CreateBid";
import ViewBids from "../Bid/View/ViewBids";
import InProgressPickups from "../Pickup/InProgress/InProgressPickups";

const AdminDashboard = () => {
    const tabs = [
        {
            label: "Scheduled Pickups",
            to: "/admin/scheduled-pickups",
            path: "/scheduled-pickups",
            icon: faCalendarCheck,
            component: <ScheduledPickups />,
        },
        {
          label: "In Progress Pickups",
          to: "/admin/in-progress-pickups",
          path: "/in-progress-pickups",
          icon: faCar,
          component: <InProgressPickups />,
        },
        {
          label: "Completed Pickups",
          to: "/admin/completed-pickups",
          path: "/completed-pickups",
          icon: faCheck,
          component: <CompletedPickups />,
        },
        {
          label: "Create Bid",
          to: "/admin/create-bid",
          path: "/create-bid",
          icon: faPenToSquare,
          component: <CreateBid />,
        },
        {
          label: "View Bids",
          to: "/admin/view-bids",
          path: "/view-bids",
          icon: faTrash,
          component: <ViewBids />,
        }
    ];

    return (
      <Dashboard tabs={tabs}>
      </Dashboard>
    );
};

export default AdminDashboard;