import unittest

from validate_actor_role_metrics import validate


class ValidateActorRoleMetricsTest(unittest.TestCase):
    def setUp(self):
        self.metrics = {
            "median_bbox_width_px": 72.0,
            "median_bbox_height_px": 74.0,
            "median_bbox_area_px2": 5000.0,
        }

    def test_no_constraints_passes(self):
        report = validate({"role_gate": {}}, self.metrics)
        self.assertTrue(report["pass"])
        self.assertEqual(0, report["constraint_count"])

    def test_minimum_constraints_pass(self):
        candidate = {"role_gate": {"metrics": {
            "min_median_bbox_width_px": 64,
            "min_median_bbox_height_px": 64,
            "min_median_bbox_area_px2": 4500,
        }}}
        report = validate(candidate, self.metrics)
        self.assertTrue(report["pass"])
        self.assertEqual(3, report["constraint_count"])

    def test_failed_constraint_is_reported(self):
        candidate = {"role_gate": {"metrics": {"min_median_bbox_area_px2": 6000}}}
        report = validate(candidate, self.metrics)
        self.assertFalse(report["pass"])
        self.assertEqual("median_bbox_area_px2", report["checks"][0]["metric"])
        self.assertFalse(report["checks"][0]["pass"])

    def test_maximum_constraint_passes(self):
        candidate = {"role_gate": {"metrics": {"max_median_bbox_width_px": 80}}}
        self.assertTrue(validate(candidate, self.metrics)["pass"])

    def test_unknown_gate_fails_closed(self):
        candidate = {"role_gate": {"metrics": {"minimum_bossiness": 1}}}
        with self.assertRaisesRegex(ValueError, "unknown role metric"):
            validate(candidate, self.metrics)

    def test_boolean_threshold_is_rejected(self):
        candidate = {"role_gate": {"metrics": {"min_median_bbox_width_px": True}}}
        with self.assertRaisesRegex(ValueError, "finite number"):
            validate(candidate, self.metrics)


if __name__ == "__main__":
    unittest.main()
