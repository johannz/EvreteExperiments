package com.rinehartlabs.rulesets;

import com.rinehartlabs.dto.Stat;
import com.rinehartlabs.dto.StatModifier;
import org.evrete.api.RhsContext;
import org.evrete.dsl.annotation.*;

import java.util.Set;

@SuppressWarnings("unused")
@RuleSet(value = "Basic RuleSet")
public class BasicRuleSet {
    public boolean notIn(StatModifier modifier, Set<StatModifier> modifiers) {
        //System.out.printf("notIn: %s, %s%n", modifier, modifiers);
        return !modifiers.contains(modifier);
    }

    @Rule(salience = -5)
    @Where(value = "$stat.type == $statModifier.type", methods = {
            @MethodPredicate(
                    method = "notIn",
                    args = {"$statModifier", "$stat.modifiers"}
            )
    })
    public void updateStatWithModifier(RhsContext ctx, @Fact("$stat") Stat $stat, @Fact("$statModifier") StatModifier $statModifier) {
        System.out.printf("updateStatWithModifier: %s, %s%n", $stat, $statModifier);
        if ($stat.getModifiers().contains($statModifier)) {
            System.out.println("Skip");
        } else {
            $stat.add($statModifier);
            ctx.update($stat);
        }
    }

    @Rule(salience = -10)
    @Where("$stat.value < 10")
    public void updateStatPoints(RhsContext ctx, @Fact("$stat") Stat $stat) {
        System.out.printf("updateStatPoints: %s%n", $stat);
        $stat.setPoints($stat.getPoints()+1);
        ctx.update($stat);
    }
}
