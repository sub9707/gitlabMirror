/** @type {import('next').NextConfig} */
const path = require("path");
const nextConfig = {
  experimental: {
    appDir: true,
    reactRefresh: false,
  },
  reactStrictMode: false,
  sassOptions: {
    includePaths: [path.join(__dirname, "styles")],
  },
};

module.exports = nextConfig;
