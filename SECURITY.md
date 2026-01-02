# Security Policy

## Environment Variables Setup

### Required Environment Variables

Before running the FitTrackAI backend services, you must set up the following environment variables:

#### AI Service
```bash
export MONGODB_URI="mongodb+srv://username:password@cluster.mongodb.net/database"
export MONGODB_DATABASE="airecommendationfitness"
export GEMINI_URL="https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"
export GEMINI_KEY="your_gemini_api_key"
```

#### Activity Service
```bash
export MONGODB_URI="mongodb+srv://username:password@cluster.mongodb.net/database"
export MONGODB_DATABASE="aiactivityfitness"
export SERVER_PORT="8082"
```

#### User Service
```bash
export DB_URL="jdbc:postgresql://localhost:5432/fitness-tut-users"
export DB_USERNAME="postgres"
export DB_PASSWORD="your_secure_password"
```

### Configuration Files

- **DO NOT** commit `application.yml` files with credentials
- Use `application.yml.example` files as templates
- Copy `.env.example` to `.env` and fill in your actual credentials
- The `.env` file is gitignored and will not be committed

### Best Practices

1. **Never commit credentials** to version control
2. **Rotate credentials** immediately if they are exposed
3. **Use strong passwords** for all database connections
4. **Keep .env files local** - they should never be pushed to Git
5. **Use different credentials** for development, staging, and production environments

## Reporting Security Issues

If you discover a security vulnerability, please email the maintainers immediately. Do not create public GitHub issues for security vulnerabilities.
