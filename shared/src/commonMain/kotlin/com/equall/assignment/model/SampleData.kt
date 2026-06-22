package com.equall.assignment.model

object SampleData {

    val discovery = HomeData(
        user = User(name = "Anshul", trustIq = TrustIq(tier = "EXCELLENT")),
        lifecycle = LoanLifecycle.Discovery(
            preQualifiedAmount = 200_000,
            tenureMonths = 12,
            exploreProducts = listOf(
                ExploreProduct(code = "PL", title = "Personal Loan", subtitle = "Up to ₹2L"),
                ExploreProduct(code = "TU", title = "Top-up Loan", subtitle = "For existing"),
                ExploreProduct(code = "CC", title = "Cards", subtitle = "Coming soon", comingSoon = true),
            ),
        ),
    )

    val pendingReview = HomeData(
        user = User(name = "Anshul", trustIq = TrustIq(tier = "EXCELLENT")),
        lifecycle = LoanLifecycle.PendingReview(
            amount = 200_000,
            steps = listOf(
                TrackerStep(
                    title = "Submitted",
                    detail = "Application received",
                    status = StepStatus.COMPLETED,
                    timestamp = "Today · 9:14 am",
                ),
                TrackerStep(
                    title = "In review",
                    detail = "Underwriting in progress · SLA 30 min",
                    status = StepStatus.CURRENT,
                    timestamp = "Started 9:15 am",
                ),
                TrackerStep(
                    title = "Decision",
                    detail = "Approved or revised offer",
                    status = StepStatus.PENDING,
                ),
                TrackerStep(
                    title = "Disbursed",
                    detail = "Bank in 30 minutes",
                    status = StepStatus.PENDING,
                ),
            ),
        ),
    )

    val activeLoan = HomeData(
        user = User(name = "Anshul", trustIq = TrustIq(tier = "EXCELLENT", score = 92, trend = "up 4 this month")),
        lifecycle = LoanLifecycle.ActiveLoan(
            outstanding = 142_000,
            tenureMonths = 8,
            segmentsTotal = 9,
            segmentsPaid = 5,
            aprPercent = 25.16,
            emiAmount = 13_000,
            dueDateLabel = "1 Jul",
            autodebitBank = "HDFC ••6411",
            nextEmiDueInDays = 6,
        ),
    )

    val all: List<HomeData> = listOf(discovery, pendingReview, activeLoan)
}
