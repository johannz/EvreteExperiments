package com.rinehartlabs;

import com.rinehartlabs.dto.Stat;
import com.rinehartlabs.dto.StatModifier;
import com.rinehartlabs.rulesets.BasicRuleSet;
import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.evrete.api.StatefulSession;

import java.io.IOException;

import static com.rinehartlabs.dto.StatType.DEXTERITY;
import static com.rinehartlabs.dto.StatType.STRENGTH;

public class Main {
    public static void main(String[] args) {
        KnowledgeService service = new KnowledgeService();
        try {
            Knowledge knowledge = service.newKnowledge("JAVA-CLASS", BasicRuleSet.class);
            //knowledge.setActivationMode(ActivationMode.DEFAULT);
            try (StatefulSession session = knowledge.newStatefulSession()) {

                Stat strength = new Stat(STRENGTH);
                Stat dexterity = new Stat(DEXTERITY);
                StatModifier strRace = new StatModifier(STRENGTH, "racial", 1);
                StatModifier strSpell = new StatModifier(STRENGTH, "spell", 2);
                StatModifier dexRace = new StatModifier(DEXTERITY, "racial", 2);
                session.insert(strength, dexterity, strRace, strSpell, dexRace);
                session.fire();

                System.out.println("---");
                session.forEachFact(System.out::println);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}