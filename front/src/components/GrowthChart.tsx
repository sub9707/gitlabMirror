import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export const options = {
  responsive: true,
  maintainAspectRatio: false,
  layout: {
    padding: 30,
  },
  scales: {
    x: {
      grid: {
        color: "rgb(210, 210, 210)",
      },
      ticks: {
        font: {
          size: 16,
          family: "SUIT-Thin",
          weight: "bold",
        },
        color: "rgb(50, 50, 50)",
      },
    },
    y: {
      grid: {
        color: "rgb(210, 210, 210)",
      },
      ticks: {
        font: {
          size: 16,
          family: "SUIT-Thin",
          weight: "bold",
        },
        color: "rgb(50, 50, 50)",
      },
    },
  },
  plugins: {
    legend: {
      position: "top" as const,
      labels: {
        font: {
          size: 16,
          family: "SUIT-Thin",
          weight: "bold",
        },
        color: "rgb(50, 50, 50)",
      },
    },
  },
};

const labels = [
  "23.04.18",
  "23.04.19",
  "23.04.20",
  "23.04.21",
  "23.04.22",
  "23.04.23",
  "23.04.24",
];

export const data = {
  labels,
  datasets: [
    {
      label: "획득 경험치",
      data: [10, 15, 23, 10, 5, 12, 7],
      borderColor: "rgb(194, 106, 246)",
      backgroundColor: "rgb(204, 128, 248)",
    },
  ],
};

export default function GrowthChart() {
  return <Line options={options} data={data} />;
}
