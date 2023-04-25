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
        color: "rgba(255, 255, 255, 0.6)",
      },
      ticks: {
        font: {
          size: 16,
          family: "SUIT-Thin",
        },
        color: "rgba(255, 255, 255, 0.6)",
      },
    },
    y: {
      grid: {
        color: "rgba(255, 255, 255, 0.6)",
      },
      ticks: {
        font: {
          size: 16,
          family: "SUIT-Thin",
        },
        color: "rgba(255, 255, 255, 0.6)",
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
        },
        color: "white",
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
      label: "EXP",
      data: [10, 15, 23, 10, 5, 12, 7],
      borderColor: "rgba(53, 162, 235)",
      backgroundColor: "rgba(53, 162, 235, 0.5)",
    },
    {
      label: "Cumulative EXP",
      data: [10, 25, 48, 58, 64, 76, 83],
      borderColor: "yellow",
      backgroundColor: "rgba(255, 255, 0, 0.5)",
    },
  ],
};

export default function GrowthChart() {
  return <Line options={options} data={data} />;
}
